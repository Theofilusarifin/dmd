<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "native_160420108", "ubaya", "native_160420108");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Add report error!';

if (isset($_POST['user_id']) && isset($_POST['meme_id'])) {
    // Get passed variable
    $user_id = $_POST['user_id'];
    $meme_id = $_POST['meme_id'];

    $sql = "SELECT * FROM reports WHERE user_id = ? AND meme_id = ?";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("ii", $user_id, $meme_id);
    $stmt->execute();
    $res = $stmt->get_result();
    $row = $res->fetch_assoc();

    // If there is no data that matched
    if (!$row) {
        $sql = "INSERT INTO reports (user_id, meme_id) VALUES (?, ?)";
        $stmt = $mysqli->prepare($sql);
        $stmt->bind_param("ii", $user_id, $meme_id);
        if ($stmt->execute()) {
            // Insert successful
            $status = 'success';
            $msg = "Add report successful!";
        } else {
            $status = 'error';
            $msg = "Add report failed!";
        }
    }
    else{
        $status = 'error';
        $msg = "You already reported this meme!";
    }
}

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
));
