<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "native_160420108", "ubaya", "native_160420108");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Update process error!';

if (isset($_POST['user_id']) && isset($_POST['first_name']) && isset($_POST['privacy_setting'])) {
    // Get passed variable
    $user_id = $_POST['user_id'];
    $first_name = $_POST['first_name'];
    $last_name = isset($_POST['last_name']) ? $_POST['last_name'] : '';
    $privacy_setting = $_POST['privacy_setting'];

    // Get all meme comments detail
    $sql = "UPDATE users SET first_name = ?, last_name = ?, privacy_setting = ? WHERE id = ?";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("ssii", $first_name, $last_name, $privacy_setting, $user_id);

    if ($stmt->execute()) {
        // Update successful
        $status = 'success';
        $msg = "Update profile successful!";
    } else {
        $status = 'error';
        $msg = "Update profile failed!";
    }
}

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
));
