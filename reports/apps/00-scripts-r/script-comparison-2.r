# Load necessary libraries
library(ggplot2)
library(dplyr)
library(ggpattern) # Load the ggpattern library
library(tidyr)

# Load your data
# If your data is stored in a CSV file named 'data.csv', you can read it as follows:
data <- read.csv("all/all.csv", sep = ";",dec=",",header=TRUE)
total_data_points <- nrow(data)

# The read_csv function above assumes your CSV file is separated by semicolons and uses a comma as the decimal point.
# If this is not the case, you might need to adjust the read_csv call accordingly, such as using read.csv for different settings.

# Transform data from wide to long format for easier plotting with ggplot
data_long <- pivot_longer(data, -MUT, names_to = "Group", values_to = "Value")

# Assuming you've already loaded your data into 'data' dataframe and transformed it into 'data_long'

# Replace underscores with spaces in the group names if necessary for readability
data_long$Group <- gsub("_", " ", data_long$Group)

# Assuming `data_long` is already loaded and prepared

# Calculate the total number of plots needed based on 100 data points increment
data_points_per_increment <- 100
total_plots <- ceiling(total_data_points / data_points_per_increment)

for (plot_number in 1:total_plots) {
  # Calculate end index for slicing the data; start always from 1 for cumulative
  end_index <- min(plot_number * data_points_per_increment, total_data_points)
  
  # Slice the data for the current cumulative plot
  data_slice <- data_long[1:end_index, ]
  
  # Generate the cumulative plot for the current slice
  p <- ggplot(data_slice, aes(x = MUT, y = Value, group = Group)) +
    geom_line(aes(linetype = Group), linewidth = my_line_width) +
    geom_point(aes(shape = Group)) +
    scale_linetype_manual(values=my_line_types) +
    scale_shape_manual(values=my_point_shapes) +
    theme_minimal() +
    labs(title = sprintf("Mutation Strategy Comparison: Cumulative up to %d Points", end_index),
         x = "Mutation Number",
         y = "Value") +
    theme(legend.position = "right")
  
  # Define the filename for the PDF
  file_name <- sprintf("cumulative_plot_up_to_%d_points.pdf", end_index)
  
  # Save the cumulative plot to a PDF file
  ggsave(file_name, plot = p, width = 8, height = 6, device = 'pdf')
}