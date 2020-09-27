import distutils.dir_util as dirUtil
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

cliPath = os.path.join(cwd, "cli", "target")
targetPath = os.path.join(cwd, "target", "result")
cliFile = os.path.join(cliPath, f"open-glass-cli-{version}.jar")

Path(targetPath).mkdir(parents=True, exist_ok=True)
dirUtil.copy_tree(os.path.join(cliPath, "libs"), targetPath)
shutil.copy(cliFile, os.path.join(targetPath, "ogs-cli.jar"))
