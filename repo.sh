#!/bin/bash

# Define the output file
OUTPUT_FILE="repo_concatenated.txt"

# Remove the output file if it already exists
rm -f "$OUTPUT_FILE"

# Find all files in the repository and concatenate them into the output file
# Exclude the output file itself and any directories
find . -type f ! -name "$OUTPUT_FILE" ! -path "./.git/*" -print0 | xargs -0 -I {} sh -c 'echo "### {} ###"; cat "{}"; echo -e "\n\n"' >> "$OUTPUT_FILE"

echo "Repository contents have been concatenated into $OUTPUT_FILE"