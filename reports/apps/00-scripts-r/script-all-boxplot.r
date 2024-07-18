library(tidyverse)

# Load your data
data <- read.csv("all/all.csv", sep = ";", dec = ",")

# Assuming your SCT columns are named SCT1, SCT2, ..., SCTn
# and your TCT columns are named TCT1, TCT2, ..., TCTn

# Gather all SCT and TCT columns, marking them as such
data_long <- data %>%
  pivot_longer(cols = starts_with("SCT"), names_to = "Variable", values_to = "Value") %>%
  mutate(Type = "SCT") %>%
  bind_rows(
    data %>%
      pivot_longer(cols = starts_with("TCT"), names_to = "Variable", values_to = "Value") %>%
      mutate(Type = "TCT")
  )

# Now, data_long contains all values with an indication if they're SCT or TCT

# Plot the boxplot
p <- ggplot(data_long, aes(x = Type, y = Value)) +
  geom_boxplot() +
  labs(title = "Comparison of SCT vs. TCT",
       x = "Type",
       y = "Value") +
  theme_minimal()

pdf_file_name <- paste0(app,"/graph-boxplot-all.pdf")

# Save the plot
ggsave(pdf_file_name, plot = p, device = "pdf", width = 10, height = 6)
