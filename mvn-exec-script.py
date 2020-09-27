import os
import shutil
from pathlib import Path

cwd = os.getcwd()

targetPath = os.path.join(cwd, "target", "result")
shellScriptName = "ogs.sh"
shellScriptPath = os.path.join(cwd, shellScriptName)

Path(targetPath).mkdir(parents=True, exist_ok=True)
shutil.copyfile(shellScriptPath, os.path.join(targetPath, shellScriptName))
