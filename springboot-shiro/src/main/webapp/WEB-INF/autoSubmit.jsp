<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="expires" content="0" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<title>"京东支付</title>
</head>
<body onload="autosubmit()">
	<form action="${payUrl}" method="post" id="batchForm">
		<input type="hidden" name="version" value="${orderInfo.version}"><br />
		<input type="hidden" name="merchant" value="${orderInfo.merchant}"><br />
		<input type="hidden" name="device" value="${orderInfo.device}"><br />
		<input type="hidden" name="tradeNum" value="${orderInfo.tradeNum}"><br />
		<input type="hidden" name="tradeName"
			value="${orderInfo.tradeName}"><br /> <input type="hidden"
			name="tradeDesc" value="${orderInfo.tradeDesc}"><br /> <input
			type="hidden" name="tradeTime" value="${orderInfo.tradeTime}"><br />
		<input type="hidden" name="amount" value="${orderInfo.amount}"><br />
		<input type="hidden" name="currency" value="${orderInfo.currency}"><br />
		<input type="hidden" name="note" value="${orderInfo.note}"><br />
		<input type="hidden" name="callbackUrl"
			value="${orderInfo.callbackUrl}"><br /> <input
			type="hidden" name="notifyUrl" value="${orderInfo.notifyUrl}"><br />
		<input type="hidden" name="ip" value="${orderInfo.ip}"><br />
<%-- 		<input type="hidden" name="userType" value="${orderInfo.userType}"><br /> --%>
		<input type="hidden" name="userId" value="${orderInfo.userId}"><br />
		<input type="hidden" name="expireTime"
			value="${orderInfo.expireTime}"><br /> 
			<input type="hidden" name="orderType" value="${orderInfo.orderType}"><br />
		<input type="hidden" name="industryCategoryCode"
			value="${orderInfo.industryCategoryCode}"><br /> <input
			type="hidden" name="specCardNo" value="${orderInfo.specCardNo}"><br />
		<input type="hidden" name="specId" value="${orderInfo.specId}"><br />
		<input type="hidden" name="specName" value="${orderInfo.specName}"><br />
<%-- 		<input type="hidden" name="payChannel" value="${orderInfo.payChannel}"><br /> --%>
		<input type="hidden" name="sign" value="${orderInfo.sign}"><br />
<%-- 		<input type="hidden" name="cert" value="${orderInfo.cert}"><br /> --%>
		<input type="hidden" name="vendorId" value="${orderInfo.vendorId}"><br />
		<input type="hidden" name="goodsInfo" value="${orderInfo.goodsInfo}"><br />
		<input type="hidden" name="orderGoodsNum" value="${orderInfo.orderGoodsNum}"><br />
		<input type="hidden" name="receiverInfo" value="${orderInfo.receiverInfo}"><br />
		<input type="hidden" name="termInfo" value="${orderInfo.termInfo}"><br />
	    <input type="hidden" name="riskInfo" value="${orderInfo.riskInfo}"><br />
		
	</form>
	<script>
	function autosubmit(){
		document.getElementById("batchForm").submit();
	}	
	</script>

</body>
</html>