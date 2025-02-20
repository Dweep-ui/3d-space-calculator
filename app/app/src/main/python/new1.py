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
    for obj_length, obj_width, obj_height in objects_dimensions:
        position = adjust_position(adjusted_positions, obj_length, obj_width)
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
    for obj_length, obj_width, obj_height in objects_dimensions:
        # Generate a new position for the object
        position = find_optimal_position(area_length, area_width, obj_length, obj_width, obj_height)
        adjusted_positions.append(position)
        ax.bar3d(*position, obj_length, obj_width, obj_height, shade=True)

    image_stream = io.BytesIO()
    plt.savefig(image_stream, format='png')
    plt.close()

    image_stream.seek(0)
    base64_image = base64.b64encode(image_stream.read()).decode('utf-8')
    return base64_image

def find_optimal_position(area_length, area_width, obj_length, obj_width, obj_height):
    x = np.random.uniform(0, area_length - obj_length)
    y = np.random.uniform(0, area_width - obj_width)
    z = 0  # Choose z=0 for ground floor

    return x, y, z

def adjust_position(adjusted_positions, length, width):
    for pos in adjusted_positions:
        if (
                pos[0] < length + pos[0] and
                pos[1] < width + pos[1]
        ):
            # Collision detected, adjust position
            length += pos[0]
            width += pos[1]

    return 0, 0, 0  # Setting z to 0

def is_collision(adjusted_positions, x, y, z, obj_length, obj_width, obj_height):
    for pos in adjusted_positions:
        if (
                pos[0] < x + obj_length and pos[0] + pos[3] > x and
                pos[1] < y + obj_width and pos[1] + pos[4] > y and
                pos[2] < z + obj_height and pos[2] + pos[5] > z
        ):
            return True

    return False
