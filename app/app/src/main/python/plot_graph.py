# plot_graph.py

import matplotlib.pyplot as plt
import io
import numpy as np

def calculate_and_return_image(num_objects, width, height, depth, area_width, area_height, area_depth):
    # Calculate and create the plot based on the input values
    x = np.arange(num_objects)
    y = np.random.rand(num_objects)
    plt.plot(x, y)

    # Save the plot to a BytesIO stream
    img_stream = io.BytesIO()
    plt.savefig(img_stream, format='png')
    img_stream.seek(0)

    # Read the image data as bytes
    img_bytes = img_stream.read()
    return img_bytes




