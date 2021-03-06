<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div class="row text-center">

	<div class="col-xl-4 col-lg-12">
       <div class="card box-shadow bg-dark">
         <div class="card-header">
           <h4 class="my-0 font-weight-normal">Basic</h4>
         </div>
         <div class="card-body">
           <h1 class="card-title pricing-card-title">$8.99 <small class="text-muted">/ mo</small></h1>
           <ul class="list-unstyled mt-3 mb-4">
             <li><span class="fa fa-check text-success"></span> LD streaming quality</li>
             <li><span class="fa fa-times text-danger"></span> Movie party creation</li>
             <li><span class="fa fa-times text-danger"></span> Developer API</li>
           </ul>
           <c:if test="${(new_account != null and plan == null) or changeplan != null}">
           		<button class="btn btn-main" type="submit" name="plan" value="basic"><h5>Choose basic</h5></button>
           </c:if>
         </div>
       </div>
   </div>
   
   <div class="col-xl-4 col-lg-12">
       <div class="card box-shadow bg-dark">
         <div class="card-header">
           <h4 class="my-0 font-weight-normal">Standard</h4>
         </div>
         <div class="card-body">
           <h1 class="card-title pricing-card-title">$12.99 <small class="text-muted">/ mo</small></h1>
           <ul class="list-unstyled mt-3 mb-4">
             <li><span class="fa fa-check text-success"></span> HD streaming quality</li>
             <li><span class="fa fa-check text-success"></span> Movie party creation</li>
             <li><span class="fa fa-times text-danger"></span> Developer API</li>
           </ul>
           <c:if test="${(new_account != null and plan == null) or changeplan != null}">
           		<button class="btn btn-main" type="submit" name="plan" value="standard"><h5>Choose standard</h5></button>
           </c:if>
         </div>
       </div>
   </div>
   
   <div class="col-xl-4 col-lg-12">
       <div class="card box-shadow bg-dark">
         <div class="card-header">
           <h4 class="my-0 font-weight-normal">Premium</h4>
         </div>
         <div class="card-body">
           <h1 class="card-title pricing-card-title">$15.99 <small class="text-muted">/ mo</small></h1>
           <ul class="list-unstyled mt-3 mb-4">
             <li><span class="fa fa-check text-success"></span> 4K streaming quality</li>
             <li><span class="fa fa-check text-success"></span> Movie party creation</li>
             <li><span class="fa fa-check text-success"></span> Developer API</li>
           </ul>
           <c:if test="${(new_account != null and plan == null) or changeplan != null}">
           		<button class="btn btn-main" type="submit" name="plan" value="premium"><h5>Choose premium</h5></button>
           </c:if>
         </div>
       </div>
   </div>
</div>