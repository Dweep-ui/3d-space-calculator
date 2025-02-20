import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import io
import base64
import random

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
        position = systematic_adjust_position(adjusted_positions, obj_length, obj_width, obj_height)
        adjusted_positions.append(position)
        ax.bar3d(*position, obj_length, obj_width, obj_height, shade=True)

    image_stream = io.BytesIO()
    plt.savefig(image_stream, format='png')
    plt.close()

    image_stream.seek(0)
    base64_image = base64.b64encode(image_stream.read()).decode('utf-8')
    return base64_image

def systematic_adjust_position(adjusted_positions, length, width, height):
   # margin = 0.1  # Adjust this margin as needed

    if not adjusted_positions:
        # Place the first object at the origin
        return 0, 0, 0

    # Find the last placed object
    last_position = adjusted_positions[-1]

    # Calculate the new position based on the last object's dimensions
    x = last_position[0] + last_position[3] + margin
    y = last_position[1]
    z = last_position[2]

    return x, y, z

def adjust_position(adjusted_positions, length, width, height):
   # margin = 0.1  # Adjust this margin as needed

    if not adjusted_positions:
        return 0, 0, 0

    last_position = adjusted_positions[-1]

    x = last_position[0] + last_position[3] + margin
    y = last_position[1]
    z = last_position[2]

    return x, y, z

def is_collision(adjusted_positions, x, y, z, obj_length, obj_width, obj_height):
    for pos in adjusted_positions:
        if (
                pos[0] < x + obj_length and pos[0] + pos[3] > x and
                pos[1] < y + obj_width and pos[1] + pos[4] > y and
                pos[2] < z + obj_height and pos[2] + pos[5] > z
        ):
            return True

    return False
