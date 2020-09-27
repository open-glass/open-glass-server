import os
import shutil
from pathlib import Path

cwd = os.getcwd()

targetPath = os.path.join(cwd, "target", "result")
shellScriptName = "ogs.sh"
batchScriptName = "ogs.bat"
shellScriptPath = os.path.join(cwd, shellScriptName)
batchScriptPath = os.path.join(cwd, batchScriptName)

Path(targetPath).mkdir(parents=True, exist_ok=True)
shutil.copyfile(shellScriptPath, os.path.join(targetPath, shellScriptName))
shutil.copyfile(batchScriptPath, os.path.join(targetPath, batchScriptName))

