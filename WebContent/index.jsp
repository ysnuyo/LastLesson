<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>
<h2>タイトルとタグと記事を入力してください。</h2>
<form action="input" method="post">
	<table>
		<tr><th>タイトル</th><td><input type="text" name="title"></td></tr>
		<tr><th>タグ</th><td><input type="text" name="tag"></td></tr>
		<tr><th>コンテンツ</th><td><textarea name="content" rows="20" cols="50"></textarea></td></tr>
	</table>
	<input type="submit" value="送信">
</form>
</body>
</html>