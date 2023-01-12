<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "native_160420108", "ubaya", "native_160420108");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Add meme error!';

if (isset($_POST['user_id']) && isset($_POST['url_img']) && isset($_POST['top_text']) && isset($_POST['bottom_text'])) {
    // Get passed variable
    $user_id = $_POST['user_id'];
    $url_img = $_POST['url_img'];
    $top_text = $_POST['top_text'];
    $bottom_text = $_POST['bottom_text'];

    // Add 7 hour because gmt+7
    $now = date("Y-m-d H:m:s");
    $created_at = date("Y-m-d H:i:s", strtotime($now . ' + 7 hours'));

    $sql = "INSERT INTO memes (user_id, url_img, top_text, bottom_text, created_at) VALUES (?, ?, ?, ?, ?)";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("issss", $user_id, $url_img, $top_text, $bottom_text, $created_at);
    if ($stmt->execute()) {
        // Insert successful
        $status = 'success';
        $msg = "Add meme succesful!";
    } else {
        $status = 'error';
        $msg = "Add meme failed!";
    }
}

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
));
