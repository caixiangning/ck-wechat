<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网页授权作用域为snsapi_userinfo获取用户基本信息</title>
</head>
<body>

用户的唯一标识：${snsapiUserInfoResponseParam.openid}
<br/>
用户昵称：${snsapiUserInfoResponseParam.nickname}
<br/>
用户的性别，值为1时是男性，值为2时是女性，值为0时是未知：${snsapiUserInfoResponseParam.sex}
<br/>
用户个人资料填写的省份：${snsapiUserInfoResponseParam.province}
<br/>
普通用户个人资料填写的城市：${snsapiUserInfoResponseParam.city}
<br/>
国家，如中国为CN：${snsapiUserInfoResponseParam.country}
<br/>
用户头像：${snsapiUserInfoResponseParam.headimgurl}
<br/>
用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）：${snsapiUserInfoResponseParam.privilege}
<br/>
只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）：${snsapiUserInfoResponseParam.unionid}

</body>
</html>