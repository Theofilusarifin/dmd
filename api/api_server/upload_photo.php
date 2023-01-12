<?php
header('Access-Control-Allow-Origin: *');

$dir = $_SERVER['DOCUMENT_ROOT'] . "/photo";

// Default Status and Message
$status = 'error';
$msg = 'Update photo error!';

if (isset($_FILES['image']['name'])) {
    $file_name = basename($_FILES['image']['name']);
    $extension = strtolower(pathinfo($file_name, PATHINFO_EXTENSION));
    if ($extension == 'png' || $extension == 'jpg' || $extension == 'jpeg') {
        if ($_FILES["image"]["size"] < 4000001) {
            $file = $dir . "/" . $file_name;
            if (move_uploaded_file($_FILES['image']['tmp_name'], $file)) {
                $status = 'success';
                $msg = 'File Uploaded!';
            } else {
                $status = 'error';
                $msg = 'Something Went Wrong Please Retry';
            }
        } else {
            $status = "error";
            $msg = "File size cant exceed 4 MB!";
        }
    } else {
        $status = "error";
        $msg =  "Only .png, .jpg and .jpeg format are accepted!";
    }
} else {
    $status = "error";
    $msg =  "Photo not uploaded!";
}

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
));
