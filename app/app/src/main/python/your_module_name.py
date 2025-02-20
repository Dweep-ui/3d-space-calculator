# your_module_name.py
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
from matplotlib.backends.backend_agg import FigureCanvasAgg
import numpy as np
from io import BytesIO

def create_axes3d_widget(width, height):
    fig = plt.Figure(figsize=(width/100, height/100), dpi=100)
    ax = fig.add_subplot(111, projection='3d')

    # Your Axes3D functionality goes here
    x = np.random.rand(10)
    y = np.random.rand(10)
    z = np.random.rand(10)
    ax.scatter(x, y, z)

    # Create a rendering canvas
    canvas = FigureCanvasAgg(fig)
    canvas.draw()

    # Save the plot to a BytesIO object
    bytes_io = BytesIO()
    canvas.tostring_rgb()
    canvas.print_rgb(bytes_io)

    # Return the BytesIO content
    return bytes_io.getvalue()


