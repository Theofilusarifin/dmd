<?php
header('Access-Control-Allow-Origin: *');

$status = 'error';
$msg = 'Upload File process error!';

if(!empty($_POST['uploaded_file']) && !empty($_POST['user_id'])){
    // upload file
    $imageFileEncoded = $_POST['uploaded_file'];
    $namaImage = "profilepicture_".$_POST['user_id'].".jpg";
    if(file_put_contents("profilpic/$namaImage", base64_decode($imageFileEncoded))){
        // Upload berhasil
        $status = 'success';
        $msg = 'Upload Image success';
    }else{
        $status = 'error';
        $msg = "Upload Image failed!";
    }
}

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
));



