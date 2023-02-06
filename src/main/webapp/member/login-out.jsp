<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.jsp" %>
		
    <div id="login-wrapper">
        <div class="container">
            <div class="heading">
             	<c:if test="${MEMBER.simei!=null}">
             		<h2>こんにちは、${MEMBER.simei}さん。</h2>
         		</c:if>
         		
         		    <br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
                	<br>
         		
          	</div>
        </div>
    </div>
        
<%@include file="../footer.jsp" %>