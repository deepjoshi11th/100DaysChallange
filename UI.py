import tkinter as tk
from tkinter import filedialog
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

def browse_file():
    file_path = filedialog.askopenfilename()
    process_file(file_path)

def process_file(file_path):
    # Load the data from the file (assuming it's a CSV file with two columns: time and value)
    data = np.loadtxt(file_path, delimiter=',')
    time = data[:, 0]
    values = data[:, 1]

    # Create a line plot
    fig = plt.Figure()
    ax = fig.add_subplot(111)
    ax.plot(time, values)

    # Customize the plot
    ax.set_title('Time Series Plot')
    ax.set_xlabel('Time')
    ax.set_ylabel('Value')

    # Display the plot in the UI
    canvas = FigureCanvasTkAgg(fig, master=frame)
    canvas.draw()
    canvas.get_tk_widget().pack(side=tk.TOP, fill=tk.BOTH, expand=1)

# Create the main window
root = tk.Tk()
root.title("File Browser and Plotter")

# Create a frame to hold the widgets
frame = tk.Frame(root)
frame.pack(padx=10, pady=10)

# Create a button to browse for a file
browse_button = tk.Button(frame, text="Browse", command=browse_file)
browse_button.pack(side=tk.TOP, pady=5)

# Run the main loop
root.mainloop()
