<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div class="card-deck mb-3 text-center">
       <div class="card mb-4 box-shadow">
         <div class="card-header">
           <h4 class="my-0 font-weight-normal">Basic</h4>
         </div>
         <div class="card-body">
           <h1 class="card-title pricing-card-title">$8.99 <small class="text-muted">/ mo</small></h1>
           <ul class="list-unstyled mt-3 mb-4">
             <li>10 users included</li>
             <li>2 GB of storage</li>
             <li>Email support</li>
             <li>Help center access</li>
           </ul>
           <c:if test="${new_user != null and plan == null}">
           		<button class="btn btn-main btn-main-light" type="submit" name="plan" value="basic"><h5>Choose basic</h5></button>
           </c:if>
         </div>
       </div>
       
       <div class="card mb-4 box-shadow">
         <div class="card-header">
           <h4 class="my-0 font-weight-normal">Standard</h4>
         </div>
         <div class="card-body">
           <h1 class="card-title pricing-card-title">$12.99 <small class="text-muted">/ mo</small></h1>
           <ul class="list-unstyled mt-3 mb-4">
             <li>20 users included</li>
             <li>10 GB of storage</li>
             <li>Priority email support</li>
             <li>Help center access</li>
           </ul>
           <c:if test="${new_user != null and plan == null}">
           		<button class="btn btn-main btn-main-light" type="submit" name="plan" value="standard"><h5>Choose standard</h5></button>
           </c:if>
         </div>
       </div>
       
       <div class="card mb-4 box-shadow">
         <div class="card-header">
           <h4 class="my-0 font-weight-normal">Premium</h4>
         </div>
         <div class="card-body">
           <h1 class="card-title pricing-card-title">$15.99 <small class="text-muted">/ mo</small></h1>
           <ul class="list-unstyled mt-3 mb-4">
             <li>30 users included</li>
             <li>15 GB of storage</li>
             <li>Phone and email support</li>
             <li>Help center access</li>
           </ul>
           <c:if test="${new_user != null and plan == null}">
           		<button class="btn btn-main btn-main-light" type="submit" name="plan" value="premium"><h5>Choose premium</h5></button>
           </c:if>
         </div>
       </div>
</div>