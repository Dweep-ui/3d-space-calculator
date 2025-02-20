import matplotlib.pyplot as plt
from chaquopy import MatplotlibView

# Function to create a 3D plot and return the Axes3D object
def create_3d_plot():
    fig, ax = plt.subplots(subplot_kw={'projection': '3d'})
    return fig, ax

# Other functions as provided in your original code
def can_zoom(ax):
    return ax.get_zlim()

def can_pan(ax):
    return ax.can_pan()

def disable_mouse_rotation(ax):
    ax.disable_mouse_rotation()

def mouse_init(ax):
    ax.mouse_init()

def drag_pan(ax, event):
    ax.drag_pan(1, event)

def format_zdata(ax, zdata):
    return ax.format_zdata(zdata)

def format_coord(ax, x, y):
    return ax.format_coord(x, y)

# Function to show the plot in a MatplotlibView
def show_plot(fig):
    view = MatplotlibView(activity)
    view.set_figure(fig)
    view.show()
