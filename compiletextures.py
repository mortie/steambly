#!/usr/bin/env python

import os
import subprocess
import shutil
import json

# Not beautiful, but I don't know any better ways to convert
# xcf to png in a script
GIMPSCRIPT = """
import gimpfu
def convert(src, dst):
    img = pdb.gimp_file_load(src, src)
    layer = pdb.gimp_image_merge_visible_layers(img, 1)
    pdb.gimp_file_save(img, layer, dst, dst)
    pdb.gimp_image_delete(img)

for f in {{FILES}}:
    convert(f[0], f[1])

pdb.gimp_quit(0)
"""

SRC="textures"
DST="src/main/resources/assets/steambly/textures"

class OprExport:
    def __init__(self, src, dst):
        self.src = src
        self.dst = dst

class OprCopy:
    def __init__(self, src, dst):
        self.src = src
        self.dst = dst

class Queue:
    def __init__(self):
        self.exports = [];
        self.copys = [];

    def append(self, opr):
        if (isinstance(opr, OprExport)):
            self.exports.append(opr)
        elif (isinstance(opr, OprCopy)):
            self.copys.append(opr)
        else:
            print("ERROR: Invalid opr "+type(opr))

    def runExports(self):
        exportstr = "[ "
        first = True
        for o in self.exports:
            print(o.src)
            print(">>> "+o.dst)
            if not first:
                exportstr += ", "
            exportstr += "[ '"+o.src+"', '"+o.dst+"' ]"
            first = False
        exportstr += " ]"
        script = GIMPSCRIPT.replace("{{FILES}}", exportstr)
        subprocess.check_output([
            "gimp", "--no-interface",
            "--no-data", "--no-fonts",
            "--batch-interpreter=python-fu-eval",
            "-b", script])

    def runCopys(self):
        for o in self.copys:
            print(o.src)
            print(">>> "+o.dst)
            shutil.copy2(o.src, o.dst)

    def run(self):
        self.runExports()
        self.runCopys()

q = Queue()

def exportdir(d):
    S = SRC+"/"+d
    D = DST+"/"+d
    if not os.path.exists(D):
        os.makedirs(D)

    for f in os.listdir(D):
        os.remove(D+"/"+f)

    for f in os.listdir(S):
        if f.endswith(".png.mcmeta"):
            q.append(OprCopy(S+"/"+f, D+"/"+f));
        elif f.endswith(".xcf"):
            png = f.replace(".xcf", ".png")
            q.append(OprExport(S+"/"+f, D+"/"+png))
        else:
            print("ERROR: Bad file name: "+f)

exportdir("blocks")

q.run();
