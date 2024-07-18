library(ggplot2)
library(scales) # For custom label formatting
library(tools)

# Uncomment one app at a time to generate plots for that app
app <- "aegis"
#app <- "amazefilemanager"
#app <- "antennapod"
#app <- "keepassdroid"  # Currently active app for plotting
#app <- "omninotes"
#app <- "simplenote"
App <- toTitleCase(app)

file_name <- paste0(app,"/",app,".csv")

# Load the data
data <- read.csv(file_name, sep = ";", dec=",", header=TRUE)

# Define increments for plotting (100, 200, 300, ...)
increments <- seq(100, nrow(data), by = 100)

# Loop through each increment and create a plot
for (i in increments) {
  # Subset the data to the current increment
  subset_data <- data[1:i, ]
  
  # Create the plot without a legend
  p <- ggplot(subset_data, aes(x = MUT)) +
    geom_line(aes(y = SCT, linetype = "SCT")) + # Line for SCH
    geom_line(aes(y = TCT, linetype = "TCT")) + # Line for TRA
    labs(title = paste(App, "Schemata x Traditional Cumulative Running Time -", i, "Data Points"), x = "Number of Mutants", y = "Time (sec)") +
    scale_linetype_manual(
      name = "", # Sets the legend title
      values = c("SCT" = "solid", "TCT" = "dashed"),
      labels = c("SCT" = "Schemata", "TCT" = "Traditional") # Sets custom names for legend items
    ) +
    scale_y_continuous(labels = label_number()) + # Format y-axis labels as regular numbers
    theme(legend.position = "bottom")  # This line moves the legend to the bottom
  
  pdf_file_name <- paste0(app,"/graph-bw-cumulative-time-", app, "-", i, "_points.pdf")
  
  # Save the plot as a PDF
  ggsave(pdf_file_name, plot = p, device = "pdf", width = 8, height = 6)
}
