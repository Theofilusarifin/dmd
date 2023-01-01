<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "native_160420108", "ubaya", "native_160420108");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Add like error!';

if (isset($_POST['user_id']) && isset($_POST['meme_id'])) {
    // Get passed variable
    $user_id = $_POST['user_id'];
    $meme_id = $_POST['meme_id'];

    $sql = "DELETE FROM likes WHERE user_id=? AND meme_id=?";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("ii", $user_id, $meme_id);
    if ($stmt->execute()) {
        // Delete successful
        $status = 'success';
        $msg = "Delete like succesful!";
    } else {
        $status = 'error';
        $msg = "Delete like failed!";
    }
}

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
));
