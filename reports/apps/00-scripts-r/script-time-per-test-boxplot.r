# Load necessary libraries
library(ggplot2)
library(tidyr)
library(tools)

app <- "simplenote"
App <- toTitleCase(app)

file_name <- paste0(app,"/",app,".csv")
# Load the data
data <- read.csv(file_name, sep = ";",dec=",",header=TRUE)

# Reshape the data to make 'SRUN' and 'TRUN' columns long format for easier plotting
data_long <- pivot_longer(data, cols = c("SRUN", "TRUN"), names_to = "Strategy", values_to = "Time (sec)")

# Define increments for plotting (100, 200, 300, ...)
increments <- seq(100, nrow(data), by = 100)

# Loop through each increment and create a plot
for (i in increments) {
  # Subset the data to the current increment
  subset_data <- data_long[1:i, ]
  
  # Correct the aes mapping in your ggplot call
  p <- ggplot(subset_data, aes(x = Strategy, y = `Time (sec)`, fill = Strategy)) +
    geom_boxplot() +
    labs(title = paste(App, "Runtime Comparison for First", i, "Data Points"), x = "Strategy", y = "Time (sec)") +
    scale_fill_manual(
      values = c("SRUN" = "blue", "TRUN" = "red"),
      labels = c("SRUN" = "Schemata", "TRUN" = "Traditional")
      ) +
    scale_x_discrete(labels = c("SRUN" = "Schemata", "TRUN" = "Traditional")) +
    theme_minimal()
  
  pdf_file_name <- paste0(app,"/graph-runtime-",app,"-", i, "-points.pdf")
  
  # Save the plot
  ggsave(pdf_file_name, plot = p, device = "pdf", width = 10, height = 6)
}
