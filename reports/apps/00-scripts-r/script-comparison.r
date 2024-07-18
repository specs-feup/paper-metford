# Load necessary libraries
library(ggplot2)
library(dplyr)
library(tools)

app <- "all"
App <- toTitleCase(app)

file_name <- paste0(app,"/",app,".csv")

# Read the CSV file
data <- read.csv(file_name, sep = ";", dec = ",", header = TRUE)

# Transform data from wide to long format
data_long <- pivot_longer(data, cols = -MUT,
                          names_to = c("Strategy", "App"),
                          names_sep = "-",
                          values_to = "Value")

# Split Strategy and App into separate columns
data_long <- data_long %>%
  mutate(Strategy = ifelse(grepl("SCT", Strategy), "Schemata", "Traditional"),
         App = case_when(grepl("AE", App) ~ "Aegis",
                         grepl("AM", App) ~ "AmazeFileManager",
                         grepl("AP", App) ~ "AntennaPod",
                         grepl("KP", App) ~ "Keepassdroid",
                         grepl("ON", App) ~ "OmniNotes",
                         grepl("SN", App) ~ "SimpleNote"))

# Function to plot data
plot_data <- function(data, max_points) {
  filtered_data <- data %>% filter(MUT <= max_points)
  
  ggplot(filtered_data, aes(x = MUT, y = Value, linetype = interaction(Strategy, App), group = interaction(Strategy, App))) +
    geom_line() +
    scale_linetype_manual(values=c("solid", "dashed", "dotted", "dotdash", "longdash", "twodash")) +
    theme_minimal() +
    theme(legend.position = "bottom",
          axis.text.x = element_text(angle = 45, hjust = 1),
          plot.title = element_text(hjust = 0.5),
          legend.title = element_blank(),
          legend.text = element_text(size = 6)) +
    labs(title = paste("Comparison of Mutation Strategies for up to", max_points, "Data Points"),
         x = "Mutation Count",
         y = "Value") +
    guides(linetype = guide_legend(override.aes = list(color = "black")))
  
  ggsave(paste("comparison_graph_", max_points, "dp.pdf", sep = ""), width = 10, height = 8)
}


# Generate plots for specified intervals
for (i in seq(100, 1218, by = 100)) {
  plot_data(data_long, i)
}