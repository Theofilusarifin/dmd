<?php

$target_path = "https://ubaya.fun/native/160420108/profilpic/";
$target_path = $target_path.basename($_FILES['uploaded_file']['name']);

if(move_uploaded_file($_FILES['uploaded_file']['name'], $target_path)){
    // Berhasil
}
else {
    // Gagal
}

?>