# Citation Downloader

A JavaFX application that helps researchers and academics efficiently extract and manage citations from PDF documents, URLs, and DOIs. The application provides a user-friendly interface for processing academic papers and downloading referenced materials.

## Features

### PDF Processing
- Extract hyperlinks and citations from PDF documents
- Automatically detect and process citation links
- Download referenced PDFs directly
- View page numbers and link text for each reference
- Organize downloads in a dedicated folder

### URL Processing
- Process academic URLs directly
- Extract citation information from web pages
- Support for various academic websites and repositories

### DOI Processing
- Direct DOI input processing
- Fetch metadata for academic papers
- Access related documents and citations

## Prerequisites

- Java Development Kit (JDK) 17 or later
- Maven 3.6 or later
- macOS, Windows, or Linux operating system
- Internet connection for downloading citations.


## Usage

### Processing PDF Documents
1. Launch the application
2. Select the "PDF Processing" tab
3. Click "Upload PDF" to choose a PDF file
4. The application will extract all hyperlinks
5. Click "Open" next to any link to:
   - Download the PDF if it's a document link
   - Open in browser if it's a web link

### Processing URLs
1. Navigate to the "URL Input" tab
2. Enter an academic URL in the text field
3. Click "Process URL" to extract citation information

### Processing DOIs
1. Go to the "DOI Input" tab
2. Enter a DOI (e.g., 10.1000/xyz123)
3. Click "Process DOI" to fetch paper information

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Code Style

This project follows Google Java Style Guide with additional requirements:
- Uses JetBrains annotations for null safety
- Comprehensive JavaDoc documentation
- Final classes and parameters where appropriate
- Clear separation of concerns

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Apache PDFBox team for PDF processing capabilities
- JavaFX team for the UI framework
- All contributors who help improve this project

## Support

For support, please open an issue in the GitHub repository or contact the maintainers.

---

Made with ❤️ for the academic community
