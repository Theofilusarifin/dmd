<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "root", "", "dmd");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Get memes error!';

// Get all meme detail
$sql = "SELECT * FROM memes ORDER BY id DESC";
$stmt = $mysqli->prepare($sql);
$stmt->execute();
$res = $stmt->get_result();

$memes = [];
while ($row = $res->fetch_assoc()) {
    // Store meme details to array
    $meme = array(
        "id" => $row['id'],
        "user_id" => $row['user_id'],
        "url_img" => $row['url_img'],
        "top_text" => $row['top_text'],
        "bottom_text" => $row['bottom_text'],
        "total_like" => $row['total_like'],
    );
    $memes[] = $meme;
}
// Set success status 
$status = 'success';
$msg = $memes;

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
));
