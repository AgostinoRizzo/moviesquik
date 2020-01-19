<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div class="row edit-row">
  <div class="col-md-5">
    <a href="#">
      <img src="res/drawable/image_placeholder.png" class="rounded-circle input-img-preview img-fluid rounded mb-3 mb-md-0">
    </a>
  </div>
  <div class="col-md-7">
  	<form class="edit-form profile-img-edit-form" enctype="multipart/form-data" action="user_upload" method="POST">
	    <h3>Profile image</h3>
	    <br>
	    <div class="custom-file">
		        <input type="file" class="custom-file-input" id="customFile" name="user-profile-img" required>
		        <label class="custom-file-label" for="customFile">Choose</label>
	    </div>
	    <p>Choose new profile image. Then use the upload button to submit.<br>Use the delete button if you do not want to display any profile image.</p>
	    <button type="submit" id="upload" class="btn btn-main"><i class="fa fa-fw fa-upload"></i> Upload</button>
	    <a id="delete" class="btn btn-danger" href="?action=delete"><i class="fa fa-fw fa-remove"></i> Delete</a>
     </form>
     
  </div>
</div>
<!-- /.row -->

<hr>