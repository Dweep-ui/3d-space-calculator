# PythonActivity.py

import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

def adjust_object(width, height, depth):
    max_x = area_width - width
    max_y = area_height - height
    max_z = area_depth - depth

    x = np.random.uniform(0, max_x)
    y = np.random.uniform(0, max_y)
    z = np.random.uniform(0, max_z)

    return {'position': [x, y, z], 'dimensions': [width, height, depth]}

def calculate_adjustments(objects, area_width, area_height, area_depth):
    adjusted_objects = []

    for obj in objects:
        width, height, depth = obj['dimensions']
        adjusted_obj = adjust_object(width, height, depth)
        adjusted_objects.append({'name': obj['name'], 'position': adjusted_obj['position'], 'dimensions': adjusted_obj['dimensions']})

    return adjusted_objects

