# cubee.py

import io
import base64
import matplotlib.pyplot as plt

def draw_2d_graph(x_values, y_values, title='2D Graph'):
    # Create a 2D graph
    plt.plot(x_values, y_values)
    plt.title(title)
    plt.xlabel('X-axis')
    plt.ylabel('Y-axis')

    # Save the plot to a BytesIO object
    img_buf = io.BytesIO()
    plt.savefig(img_buf, format='png')
    img_buf.seek(0)

    # Encode the image buffer in base64
    encoded_image = base64.b64encode(img_buf.read()).decode('utf-8')

    plt.close()  # Close the plot to free up resources
    return encoded_image
def draw_3d_graph(x_values, y_values, z_values, title='3D Graph'):
    # Create a 3D graph
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')
    ax.scatter(x_values, y_values, z_values)
    ax.set_title(title)
    ax.set_xlabel('X-axis')
    ax.set_ylabel('Y-axis')
    ax.set_zlabel('Z-axis')

    # Save the plot to a BytesIO object
    img_buf = io.BytesIO()
    plt.savefig(img_buf, format='png')
    img_buf.seek(0)

    # Encode the image buffer in base64
    encoded_image = base64.b64encode(img_buf.read()).decode('utf-8')

    plt.close()  # Close the plot to free up resources
    return encoded_image