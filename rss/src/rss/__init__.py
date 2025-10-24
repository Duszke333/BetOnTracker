from .router import app
import uvicorn
import logging

def main() -> None:
    logging.basicConfig(level=logging.INFO)
    logging.info("RSS module initialized.")
    uvicorn.run(app, host="0.0.0.0", port=8000)
    logging.info("RSS module application started.") 