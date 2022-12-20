<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "root", "", "dmd");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Add comment error!';

if (isset($_POST['user_id']) && isset($_POST['meme_id']) && isset($_POST['content'])) {
    // Get passed variable
    $user_id = $_POST['user_id'];
    $meme_id = $_POST['meme_id'];
    $content = $_POST['content'];

    $sql = "INSERT INTO comments (user_id, meme_id, content, created_at) VALUES (?, ?, ?, NOW())";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("iis", $user_id, $meme_id, $content);
    if ($stmt->execute()) {
        // Insert successful
        $status = 'success';
        $msg = "Add comment succesful";
    } else {
        $status = 'error';
        $msg = "Add comment failed!";
    }
}

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
));
