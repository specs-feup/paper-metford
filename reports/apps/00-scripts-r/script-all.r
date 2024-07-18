# Load necessary libraries
library(ggplot2)
library(dplyr)
library(ggpattern) # Load the ggpattern library
library(tidyr)

# Load your data
# If your data is stored in a CSV file named 'data.csv', you can read it as follows:
data <- read.csv("all/all.csv", sep = ";",dec=",",header=TRUE)

# The read_csv function above assumes your CSV file is separated by semicolons and uses a comma as the decimal point.
# If this is not the case, you might need to adjust the read_csv call accordingly, such as using read.csv for different settings.

# Transform data from wide to long format for easier plotting with ggplot
data_long <- pivot_longer(data, -MUT, names_to = "Group", values_to = "Value")

# Assuming you've already loaded your data into 'data' dataframe and transformed it into 'data_long'

# Replace underscores with spaces in the group names if necessary for readability
data_long$Group <- gsub("_", " ", data_long$Group)

# Define custom line types and point shapes
my_line_types <- rep(c("solid", "dashed", "dotted", "dotdash", "longdash", "twodash"), each=2)
my_line_width <- 1
my_point_shapes <- 0:11  # Selecting 12 distinct point shapes

# Updated plot command
p <- ggplot(data_long, aes(x = MUT, y = Value, group = Group)) +
  geom_line(aes(linetype = Group), linewidth = my_line_width) +  # Use 'linewidth' instead of 'size'
  geom_point(aes(shape = Group)) +    # Use different point shapes for each group
  scale_linetype_manual(values=my_line_types) +
  scale_shape_manual(values=my_point_shapes) +
  theme_minimal() +
  labs(title = "Mutation Strategy Comparison",
       x = "Mutation Number",
       y = "Value") +
  theme(legend.position = "right")

# Assuming your plot is assigned to `p`
ggsave("all_plot.pdf", plot = p, width = 8, height = 6, device = 'pdf')