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

// Get users detail
$sql = "SELECT * FROM users";
$stmt = $mysqli->prepare($sql);
$stmt->execute();
$res = $stmt->get_result();

$users = [];
while ($row = $res->fetch_assoc()) {
    $user = array(
        "id" => $row['id'],
        "first_name" => $row['first_name'],
        "last_name" => $row['last_name'],
        "url_img" => $row['url_img'],
        "privacy_setting" => $row['privacy_setting'],
        "total_like" => 0,
    );
// Assign data into array users
    $users[] = $user;
}

if (count($users) > 0){
    // Get leaderboard detail
    $sql = "SELECT m.user_id as user_id, count(l.user_id) as total_like FROM `likes` l INNER JOIN memes m on l.meme_id = m.id GROUP BY m.user_id";
    $stmt = $mysqli->prepare($sql);
    $stmt->execute();
    $res = $stmt->get_result();

    while ($row = $res->fetch_assoc()) {
        // Insert total like into detail users
        $users[array_search($row['user_id'], array_column($users, 'id'))]['total_like'] = $row['total_like'];
    }
    
    // Sort Array
    array_multisort(array_column($users, "total_like"), SORT_DESC, $users);
    // Set success status 
    $status = 'success';
    $msg = "Get leaderboard successful!";
}


// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
    "leaderboards" => $users,
));
