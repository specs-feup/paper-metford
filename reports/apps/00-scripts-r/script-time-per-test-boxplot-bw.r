# Load necessary libraries
library(ggplot2)
library(tidyr)
library(tools)

# Uncomment one app at a time to generate plots for that app
#app <- "aegis"
#app <- "amazefilemanager"
#app <- "antennapod"
#app <- "keepassdroid"  # Currently active app for plotting
#app <- "omninotes"
app <- "simplenote"
App <- toTitleCase(app)

file_name <- paste0(app,"/",app,".csv")

# Load the data
data <- read.csv(file_name, sep = ";", dec=",", header=TRUE)

# Reshape the data to make 'SRUN' and 'TRUN' columns long format for easier plotting
data_long <- pivot_longer(data, cols = c("SRUN", "TRUN"), names_to = "Strategy", values_to = "Time (sec)")

# Define increments for plotting (100, 200, 300, ...)
increments <- seq(100, nrow(data), by = 100)

# Loop through each increment and create a plot
for (i in increments) {
  # Subset the data to the current increment
  subset_data <- data_long[1:i, ]
  
  # Create the plot without a legend
  p <- ggplot(subset_data, aes(x = Strategy, y = `Time (sec)`)) +
    geom_boxplot(fill = "white", color = "black") +
    labs(title = paste(App, "Individual Test Case Runtime Comparison for", i, "Data Points"), x = "Strategy", y = "Time (sec)") +
    scale_x_discrete(labels = c("SRUN" = "Schemata", "TRUN" = "Traditional")) +
    theme_minimal() +
    theme(legend.position = "none")  # This line removes the legend
  
  pdf_file_name <- paste0(app,"/graph-bw-individual-test-runtime-",app,"-", i, "-points.pdf")
  
  # Save the plot
  ggsave(pdf_file_name, plot = p, device = "pdf", width = 10, height = 6)
}
