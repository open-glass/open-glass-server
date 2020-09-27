import os
import shutil
from pathlib import Path

cwd = os.path.dirname(os.getcwd())

configFile = os.path.join(cwd, "open-glass-common", "target", "config.properties")
targetPath = os.path.join(cwd, "target", "result")

Path(targetPath).mkdir(parents=True, exist_ok=True)
shutil.copy(configFile, os.path.join(targetPath, "config.properties"))
