
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import numpy as np
import io
import base64

def generate_graph(area_length, area_width, area_height, objects_dimensions):
    objects_dimensions = sorted([list(map(float, obj.split(','))) for obj in objects_dimensions.split(';') if obj], key=lambda x: x[0]*x[1]*x[2], reverse=True)

    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')

    ax.set_xlim([0, area_length])
    ax.set_ylim([0, area_width])
    ax.set_zlim([0, area_height])
    ax.set_xlabel('X')
    ax.set_ylabel('Y')
    ax.set_zlabel('Z')

    adjusted_positions = []
    for i, obj in enumerate(objects_dimensions):
        obj_length, obj_width, obj_height = obj
        position = adjust_position(adjusted_positions, obj_length, obj_width, obj_height)
        adjusted_positions.append(position)
        ax.bar3d(*position, obj_length, obj_width, obj_height, shade=True)

    image_stream = io.BytesIO()
    plt.savefig(image_stream, format='png')
    plt.close()

    image_stream.seek(0)
    base64_image = base64.b64encode(image_stream.read()).decode('utf-8')
    return base64_image

def re_adjust_graph(area_length, area_width, area_height, objects_dimensions):
    objects_dimensions = sorted([list(map(float, obj.split(','))) for obj in objects_dimensions.split(';') if obj], key=lambda x: x[0]*x[1]*x[2], reverse=True)

    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')

    ax.set_xlim([0, area_length])
    ax.set_ylim([0, area_width])
    ax.set_zlim([0, area_height])
    ax.set_xlabel('X')
    ax.set_ylabel('Y')
    ax.set_zlabel('Z')

    adjusted_positions = []
    for i, obj in enumerate(objects_dimensions):
        obj_length, obj_width, obj_height = obj
        position = adjust_position(adjusted_positions, obj_length, obj_width, obj_height)
        adjusted_positions.append(position)
        ax.bar3d(*position, obj_length, obj_width, obj_height, shade=True)

    image_stream = io.BytesIO()
    plt.savefig(image_stream, format='png')
    plt.close()

    image_stream.seek(0)
    base64_image = base64.b64encode(image_stream.read()).decode('utf-8')
    return base64_image

def adjust_position(adjusted_positions, length, width, height):
    for pos in adjusted_positions:
        if (
                pos[0] < length + pos[0] and
                pos[1] < width + pos[1] and
                pos[2] < height + pos[2]
        ):
            # Collision detected, adjust position
            length += pos[0]
            width += pos[1]
            height += pos[2]

    return length, width, height

