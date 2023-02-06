<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.jsp" %>

		<div id="cart-wrapper">
			<div class="container">
				<div class="heading">
					<h2>Order Status</h2>
					<p class="translation">受注状況</p>
				</div>
				<div class="cartArea">
					<div class="cartArea-right">
						<c:forEach var = "order" items = "${ORDERLIST}">
							<table>	
								<tr>
									<td>${order.date}</td>
									<td>${order.simei}</td>
									<td>${order.charge_id}</td>
									<td>${order.totalprice}</td>
									<td>${order.name}</td>
									<td>${order.price}</td>
									<td>${order.count}</td>
									<td><a href = "CartRemove.action">削除</a></td>
								</tr>
							</table>
						</c:forEach>
					</div>
				</div>
				<br>
				<br>
				<hr>
			</div>
		</div>
<%@include file="../footer.jsp" %>