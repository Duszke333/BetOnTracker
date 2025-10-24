from .router import app
import uvicorn
import logging

def main() -> None:
    logging.basicConfig(level=logging.INFO)
    logging.info("News module initialized.")
    uvicorn.run(app, host="0.0.0.0", port=8000)