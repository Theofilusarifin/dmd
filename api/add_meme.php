<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "root", "", "dmd");
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

    $sql = "INSERT INTO memes (user_id, url_img, top_text, bottom_text) VALUES (?, ?, ?, ?)";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("isss", $user_id, $url_img, $top_text, $bottom_text);
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
