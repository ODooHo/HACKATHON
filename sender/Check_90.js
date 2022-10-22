//const spawn = require('child_process').spawn;
//const result = spawn('python', ['print.py'));
//js에서 파이썬 코드 실행


const video = document.getElementById("video");
const canvas = document.getElementById("canvas");
const context = canvas.getContext("2d");
const result_label = document.getElementById("result_label");
let pose_status = 2;
let keep_time = [0, 0, 0];
let result_message = "";
navigator.mediaDevices.getUserMedia({video: true, audio: false}).then(function (stream) {
    video.srcObject = stream;
});

//then 안쪽이 function(model){} 이렇게 쓰는거랑 같다 (인자가 하나라 중괄호가 없는 것)
posenet.load().then((model) => {
    // 이곳의 model과 아래 predict의 model은 같아야 한다.
    video.onloadeddata = (e) => {
        predict();
    };

    function predict() {

        model.estimateSinglePose(video).then((pose) => {
            canvas.width = video.width;
            canvas.height = video.height;
            drawKeypoints(pose.keypoints, 0.6, context);
            drawSkeleton(pose.keypoints, 0.6, context);

            find_state(pose);
        });
        requestAnimationFrame(predict); 
    }
});
function sleep(ms) {
    const wakeUpTime = Date.now() + ms;
    while (Date.now() < wakeUpTime) {}
}
let count_time = setInterval(function () {
    if (keep_time[pose_status] == 0) {
        keep_time[0] = keep_time[1] = keep_time[2] = 0;
        keep_time[pose_status]++;
    } else {
        if (pose_status == 0){
            window.parent.postMessage({message: `Left arm ${keep_time[pose_status]}times detecteds.`}, "*");
            //window.alert("left");
            const spawn = require('child_process').spawn;
            const result = spawn("python3", ['send_left.py']);
            
        }
        else if (pose_status == 1){
            window.parent.postMessage({message: `Right arm ${keep_time[pose_status]}times detected.`}, "*");
            //window.alert("right");
            const spawn = require('child_process').spawn;
            const result = spawn("python3", ['send_right.py']);
        }
        else if(pose_status == 2){ 
            window.parent.postMessage({message: `default`}, "*");
            }

        if (pose_status != 2 && keep_time[pose_status] == 5) {
            if (pose_status == 0) {
                result_message = "left";
            } else {
                result_message = "right";
            }
            clearInterval(count_time);
            window.parent.postMessage({message: result_message}, "*");
        }
        keep_time[pose_status]++; 
    }
}, 1000);

function find_state(pose) {
    if (!left_check(pose) && !right_check(pose)) {
        pose_status = 2;
    } else if (left_check(pose)) {
        pose_status = 0;
    } else if (right_check(pose)) {
        pose_status = 1;
    }
}

function left_check(pose){
    head = pose.keypoints[0].position;
    ls = pose.keypoints[5].position;
    le = pose.keypoints[7].position;
    lw = pose.keypoints[9].position;
    b = pose.keypoints[12].position;
    if (b.y > lw.y && b.y > le.y && lw.x < le.x) {   //베이스 상태 정의(돌발상황, 왼쪽 판별)
        return true
    }else{
        return false
    }   
}

function right_check(pose){
    head = pose.keypoints[0].position
    rs = pose.keypoints[6].position;
    re = pose.keypoints[8].position;
    rw = pose.keypoints[10].position;
    b = pose.keypoints[12].position;

    if (b.y > rw.y && b.y > re.y && rw.x > re.x){ //사실상 측정하는 지표는, 손목이 팔꿈치보다 더 큰(절댓값)위치에 있냐 인듯
        return true
    }else{
        return false
    }
}


const color = "aqua";
const boundingBoxColor = "red";
const lineWidth = 2;
function toTuple({y, x}) {
    return [y, x];
}

function drawPoint(ctx, y, x, r, color) {
    ctx.beginPath();
    ctx.arc(x, y, r, 0, 2 * Math.PI);
    ctx.fillStyle = color;
    ctx.fill();
}

function drawSegment([ay, ax], [by, bx], color, scale, ctx) {
    ctx.beginPath();
    ctx.moveTo(ax * scale, ay * scale);
    ctx.lineTo(bx * scale, by * scale);
    ctx.lineWidth = lineWidth;
    ctx.strokeStyle = color;
    ctx.stroke();
}

function drawSkeleton(keypoints, minConfidence, ctx, scale = 1) {
    const adjacentKeyPoints = posenet.getAdjacentKeyPoints(keypoints, minConfidence);
    adjacentKeyPoints.forEach((keypoints) => {
        drawSegment(toTuple(keypoints[0].position), toTuple(keypoints[1].position), color, scale, ctx);
    });
}

function drawKeypoints(keypoints, minConfidence, ctx, scale = 1) {
    for (let i = 0; i < keypoints.length; i++) {
        const keypoint = keypoints[i];
        if (keypoint.score < minConfidence) {
            continue;
        }
        const {y, x} = keypoint.position;
        drawPoint(ctx, y * scale, x * scale, 3, color);
    }
}

function drawBoundingBox(keypoints, ctx) {
    const boundingBox = posenet.getBoundingBox(keypoints);
    ctx.rect(
        boundingBox.minX,
        boundingBox.minY,
        boundingBox.maxX - boundingBox.minX,
        boundingBox.maxY - boundingBox.minY
    );
    ctx.strokeStyle = boundingBoxColor;
    ctx.stroke();
}
        