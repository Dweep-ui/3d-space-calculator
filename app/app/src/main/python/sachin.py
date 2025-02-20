

import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import numpy as np
import io
import base64

def generate_graph(area_length, area_width, area_height, objects_dimensions):
    # Split the objects dimensions string
    objects_dimensions = [list(map(float, obj.split(','))) for obj in objects_dimensions.split(';') if obj]

    # Your 3D graph generation code here (using Matplotlib and Numpy)
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')

    # Plot the area box
    ax.set_xlim([0, area_length])
    ax.set_ylim([0, area_width])
    ax.set_zlim([0, area_height])
    ax.set_xlabel('X')
    ax.set_ylabel('Y')
    ax.set_zlabel('Z')

    # Plot each object at its specified coordinates
    for obj in objects_dimensions:
        obj_length, obj_width, obj_height = obj
        ax.bar3d(obj[0], obj[1], obj[2], obj_length, obj_width, obj_height, shade=True)

    # Save the plot to a BytesIO object
    image_stream = io.BytesIO()
    plt.savefig(image_stream, format='png')
    plt.close()

    # Convert the image to base64
    image_stream.seek(0)
    base64_image = base64.b64encode(image_stream.read()).decode('utf-8')
    return base64_image

