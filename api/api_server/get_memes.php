<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "native_160420108", "ubaya", "native_160420108");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Get memes error!';
$memes = [];

// Check passed parameter
if (isset($_POST['user_id']) && isset($_POST['sort_by'])) {
    // Get passed variable
    $user_id = $_POST['user_id'];
    $sort_by = $_POST['sort_by'];

    // get all meme in database
    $sql = "SELECT * FROM memes";
    $stmt = $mysqli->prepare($sql);
    $stmt->execute();
    $res = $stmt->get_result();

    $memes = [];
    while ($row = $res->fetch_assoc()) {
        $meme = array(
            "id" => $row['id'],
            "user_id" => $row['user_id'],
            "url_img" => $row['url_img'],
            "top_text" => $row['top_text'],
            "bottom_text" => $row['bottom_text'],
            "created_at" => $row['created_at'],
            // Set default to 0
            "total_like" => 0,
            "total_comment" => 0,
            "total_report" => 0,
            "liked" => false,
        );
        // Store to the array memes
        $memes[] = $meme;
    }

    // Get meme like detail
    $sql = "SELECT m.id, count(l.meme_id) as total_like FROM likes l INNER JOIN memes m on m.id = l.meme_id GROUP BY l.meme_id";
    $stmt = $mysqli->prepare($sql);
    $stmt->execute();
    $res = $stmt->get_result();
    while ($row = $res->fetch_assoc()) {
        // Insert total like into detail memes
        $memes[array_search($row['id'], array_column($memes, 'id'))]['total_like'] = $row['total_like'];
    }

    // Get meme comment detail
    $sql = "SELECT m.id, count(c.meme_id) as total_comment FROM comments c INNER JOIN memes m on m.id = c.meme_id GROUP BY c.meme_id";
    $stmt = $mysqli->prepare($sql);
    $stmt->execute();
    $res = $stmt->get_result();
    while ($row = $res->fetch_assoc()) {
        // Insert total comment into detail memes
        $memes[array_search($row['id'], array_column($memes, 'id'))]['total_comment'] = $row['total_comment'];
    }

    // Get meme report detail
    $sql = "SELECT m.id, count(r.meme_id) as total_report FROM reports r INNER JOIN memes m on m.id = r.meme_id GROUP BY r.meme_id";
    $stmt = $mysqli->prepare($sql);
    $stmt->execute();
    $res = $stmt->get_result();
    while ($row = $res->fetch_assoc()) {
        // Insert total comment into detail memes
        $memes[array_search($row['id'], array_column($memes, 'id'))]['total_report'] = $row['total_report'];
    }

    // MEME DATA ALREADY COMPLETE

    // If there is memes that liked by login user
    $sql = "SELECT meme_id FROM likes WHERE user_id = ?";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("i", $user_id);
    $stmt->execute();
    $res = $stmt->get_result();
    while ($row = $res->fetch_assoc()) {
        // Change like to true if already liked
        $memes[array_search($row['meme_id'], array_column($memes, 'id'))]['liked'] = true;
    }

    // Get meme id that already reported by login user
    $sql = "SELECT meme_id FROM reports WHERE user_id = ?";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("i", $user_id);
    $stmt->execute();
    $res = $stmt->get_result();

    $meme_reported_id = [];
    // If there is memes that reported by login user
    if ($res->num_rows > 0) {
        while ($row = $res->fetch_assoc()) {
            // Store the meme id in array
            array_push($meme_reported_id, $row['meme_id']);
        }
    }

    // Remove meme that already reported by login user or 3 times reported by others
    $fix_memes = [];
    foreach ($memes as $meme) {
        // Remove meme that already reported by login user
        if (!in_array($meme['id'], $meme_reported_id)) {
            // Remove meme that already 3 times reported
            if ($meme['total_report'] < 3) {
                $fix_memes[] = $meme;
            }
        }
    }

    // Sorting
    // Sort by the newest created
    if ($sort_by == "Newest") {
        array_multisort(array_column($fix_memes, "created_at"), SORT_DESC, $fix_memes);
    }
    // Sort by the most commented
    else if ($sort_by == "On Trending") {
        array_multisort(array_column($fix_memes, "total_comment"), SORT_DESC, $fix_memes);
    }
    // Sort by the most liked
    else if ($sort_by == "Most popular") {
        array_multisort(array_column($fix_memes, "total_like"), SORT_DESC, $fix_memes);
    }

    // Set success status 
    $status = 'success';
    $msg = 'Get memes successful!';
}
// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
    "memes" => $fix_memes,
));