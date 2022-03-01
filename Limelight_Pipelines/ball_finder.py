import cv2
import numpy as np

def cvt_dec_to_hsv(clrs):
    h, s, v = clrs
    return int(h*255), int(s*255), int(v*255)

# Color constants in HSV space
color_ranges = {
    "blue": {
        "lower": cvt_dec_to_hsv(16.83, 165.75, 255),
        "upper": cvt_dec_to_hsv(20.4, 170.85, 255)
    },
    "red": {
        "lower": (255, 69, 76),
        "upper": (255, 74, 82)
    }
}
min_radius = 5  # pixels
blur_amount = (3, 3)  # numbers must match
circle_annotation_color = (90, 255, 255)
centroid_annotation_color = (0, 255, 255)

def runPipeline(image, llrobot):
    """runPipeline() is called every frame by Limelight's backend.
    Use it to create a custom image processing pipeline.

    Args:
        image (blob): RGB image data
        llrobot (list): NetworkTables numeric array

    Returns:
        largestCountour (???): OpenCV contours data
        image (blob): image frame, RGB, potentially modified by this func
        llpython (list): NetworkTables numeric array
    """
    # convert input image to HSV and apply a small blur
    image = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    image_processed = cv2.GaussianBlur(image, blur_amount, 0)
    
    # convert to a binary image, removing pixels that don't fall between the upper
    # and lower ranges.
    if llrobot[0] == -1:
        color = "red"
    else:
        color = "blue"
    # color = "blue"
    img_threshold = cv2.inRange(image_processed, color_ranges[color]["lower"], color_ranges[color]["upper"])
    img_threshold = cv2.erode(img_threshold, None, iterations=2)
    img_threshold = cv2.dilate(img_threshold, None, iterations=2)
    
    # find the contours
    contours, _ = cv2.findContours(img_threshold, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    largestContour = np.array([[]])

    # initialize and empty array of values to send back to the robot
    llpython = [0, 0, 0, len(contours)]
    center = (0,0)
    circle_found = 0
    radius = 0
    x = 0
    y = 0

    # proceed only if at least one contour was found
    if len(contours) > 0:
        # find the largest contour, then use it to compute the minimum
        # enclosing cirle and the centroid
        largestContour = max(contours, key = cv2.contourArea)
        ((x, y), radius) = cv2.minEnclosingCirle(largestContour)
        M = cv2.moments(largestContour)
        center = (int(M["m10"] / M["m00"]), int(m["m01"] / M["m00"]))
        if radius > min_radius:
            circle_found = 1
            # draw a circle and the centroid on the image frame
            cv2.circle(image, (int(x), int(y)), int(radius), circle_annotation_color, 2)
            cv2.circle(image, center, 5, centroid_annotation_color, -1)
        # compose data to return to robot
        llpython = [circle_found, int(x), int(y), int(radius)]

    cv2.circle(image, (100,100), 5, circle_annotation_color, 2)
    image = cv2.cvtColor(image, cv2.COLOR_HSV2BGR)
    return largestContour, image, llpython