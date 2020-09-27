import distutils.dir_util as dir_util
import os
import shutil
import sys
from pathlib import Path

argCount: int = len(sys.argv)
if argCount != 2:
    print('You need to pass the desired version as a parameter. (e.g. 1.0-SNAPSHOT)')
    sys.exit(1)
version: str = sys.argv[1]
cwd = os.path.dirname(os.getcwd())

serverPath = os.path.join(cwd, "open-glass-server", "target")
targetPath = os.path.join(cwd, "target", "result")
serverFile = os.path.join(serverPath, f"open-glass-server-{version}.jar")

Path(targetPath).mkdir(parents=True, exist_ok=True)
dir_util.copy_tree(os.path.join(serverPath, "libs"), targetPath)
shutil.copy(serverFile, os.path.join(targetPath, "ogs.jar"))
