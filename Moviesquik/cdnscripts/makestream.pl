#!/usr/bin/perl

$target_directory = "./streams";

#
# get all input data and checks.
#
$source_filename = shift or die usage();
die "$source_filename does not exists." if not -f $source_filename;
die "Invalid $source_filename extension." if $source_filename !~ m/\.mp4$/;

$segment_time = shift or die usage();
die "Invalid <segment_time>." if $segment_time !~ m/^\d+$/ or $segment_time == 0;

$media_id = shift or die usage();
die "Invalid <media_id>." if $media_id !~ m/^\d+$/;

die "Too much arguments." if @ARGV > 0;
die "$target_directory does not exists." if not -d $target_directory;

$target_root = "$target_directory/mc-$media_id";
die "$target_root already exists." if -d $target_root;


#
# check installed ffmpeg command.
#
die "ffmpeg not installed." if qx{ffmpeg -version} !~ m/ffmpeg version/;

#
# get source duration.
#
$source_duration = get_media_duration($source_filename);
die "Invalid source duration." if not $source_duration;
#print "Source duration: $source_duration\n";

$num_segments = int ( $source_duration / $segment_time );
die "No segments to make." if not $num_segments;
#print "Total segments: $num_segments\n";

#
# make target root directory
#
qx{mkdir $target_root};

#
# make all segments and manifest file.
#
qx{ffmpeg -i $source_filename -map 0 -c copy -f segment -segment_time $segment_time $target_root/temp_segment_%d.mp4};
@manifest = ();
$start_time = 0.0;
foreach( qx{ls $target_root} )
{
	chomp;
	if ( m/_(\d+)\.mp4$/ )
	{
		$temp_segment_filename = "$target_root/$_";
		$segment_filename = "$target_root/segment_$1.mp4";
		qx{ffmpeg -i $temp_segment_filename -c copy -f mp4 -movflags empty_moov+default_base_moof+frag_keyframe $segment_filename};
		qx{rm $temp_segment_filename};
		push @manifest, $start_time;
		$start_time += get_media_duration("$segment_filename");
	}
}


#
# create manifest file.
#
$manifest_filename = "$target_root/manifest";
open $MANIFEST, ">", $manifest_filename or die "Cannot open $manifest_filename: $!";
print $MANIFEST "$source_duration\n";
print $MANIFEST scalar @manifest . "\n";
print $MANIFEST "$_\n" foreach (@manifest );
close $MANIFEST or die "Cannot close $manifest_filename: $!";

print "Stream segments successfully maked at $target_root\n";


sub usage
{
	return "Usage: $0 <source_file> <segment_time> <media_id>\n" .
	       "\t<source_file>:       full video source file.\n" .
	       "\t<segment_time>:      duration of each segment.\n" .
	       "\t<media_id>:          media content identificator number.\n";
}
sub get_media_duration
{
	$media_filename = shift or return 0;
	foreach ( qx{ffmpeg -i $media_filename 2>&1} )
	{
		return ($1 * 3600) + ($2 * 60) + $3 if m/^\s*Duration:\s(\d+):(\d+):(\d+\.\d+)/;
	}
}
