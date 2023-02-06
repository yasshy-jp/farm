<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.jsp" %>

		<div id="purchase-wrapper">
			<div class="container">
				<div class="heading">
					<h2>購入手続き</h2>
					<p class="translation">Purchase</p>
					<p class="translation_notes"><i class="fas fa-lock"></i>ベータ版のため実販売停止中</p>
				</div>
				<div class="purchaseArea">
					<div class="purchase-left">
						<p class="total">合計${TOTALCOUNT}個（${CART.size()}種類）</p>
						<p>
							<strong class="totalPrice">¥${TOTALPRICE_TAXIN}</strong>
							<span class="tax">（内消費税¥${TOTALPRICE_TAXIN - TOTALPRICE}）</span>
						</p>
						<hr>
						<form action="Purchase.action?price=${TOTALPRICE_TAXIN}" method="post">
							<script
								type="text/javascript"
								src="https://checkout.pay.jp/"
								class="payjp-button"
								data-key="pk_test_6a2dbf44aa8cc3b35598174c"
								data-submit-text="確定（トークンを作成）"
								data-partial="true">
							</script>
							<hr>
							<input type="checkbox" name="registerCard"> カード情報を登録
							<p> ＊次回購入より入力不要でお支払いいただけます。</p>
							<hr>
							<input type="submit" value="支払いを確定" class="btn btnBig c️onfirm">
						</form>
					</div>
					<div class="purchase-right">
						<table>
							<c:forEach var="item" items="${CART}">
								<tr>
									<td class="line1">商品${item.product.id}</td>
									<td class="line2"><img src="image/${item.product.id}.jpg" height="96"></td>
									<td class="line3">${item.product.name}</td>
									<td class="line4">${item.product.price}円</td>
									<td class="line5">数量:${item.count}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>	
			</div>
		</div>

<%@include file="../footer.jsp" %>