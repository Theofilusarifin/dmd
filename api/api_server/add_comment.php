<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "native_160420108", "ubaya", "native_160420108");
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

    // Add 7 hour because gmt+7
    $now = date("Y-m-d H:i:s");
    $created_at = date("Y-m-d H:i:s", strtotime($now . ' + 7 hours'));

    $sql = "INSERT INTO comments (user_id, meme_id, content, created_at) VALUES (?, ?, ?, ?)";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("iiss", $user_id, $meme_id, $content, $created_at);
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
