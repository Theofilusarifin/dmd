<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "native_160420108", "ubaya", "native_160420108");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Get leaderboard error!';
$leaderboards = [];

// Get leaderboard detail
$sql = "SELECT u.*, count(l.meme_id) as total_like FROM users u LEFT JOIN likes l on u.id = l.user_id LEFT JOIN memes m on m.id = l.meme_id GROUP BY u.id ORDER BY total_like DESC";
$stmt = $mysqli->prepare($sql);
$stmt->execute();
$res = $stmt->get_result();

while ($row = $res->fetch_assoc()) {
    // Store leaderboard details to array
    $leaderboard = array(
        "id" => $row['id'],
        "first_name" => $row['first_name'],
        "last_name" => $row['last_name'],
        "url_img" => $row['url_img'],
        "privacy_setting" => $row['privacy_setting'],
        "total_like" => $row['total_like'],
    );
    $leaderboards[] = $leaderboard;
}
// Set success status 
$status = 'success';
$msg = "Get leaderboard successful!";

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
    "leaderboards" => $leaderboards,
));
