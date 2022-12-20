<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "root", "", "dmd");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Get leaderboard error!';

// Get leaderboard detail
$sql = "SELECT u.*, SUM(m.total_like) as total_like FROM memes m INNER JOIN users u on m.user_id = u.id GROUP BY u.id ORDER BY total_like DESC";
$stmt = $mysqli->prepare($sql);
$stmt->execute();
$res = $stmt->get_result();

$leaderboards = [];
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
$msg = $leaderboards;

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
));
