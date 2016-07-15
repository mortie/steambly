#!/usr/bin/env python

import os
import subprocess
import shutil

# Not beautiful, but I don't know any better ways to convert
# xcf to png in a script
GIMPSCRIPT = """
import gimpfu
def convert(src, dst):
    img = pdb.gimp_file_load(src, src)
    layer = pdb.gimp_image_merge_visible_layers(img, 1)
    pdb.gimp_file_save(img, layer, dst, dst)
    pdb.gimp_image_delete(img)

convert("{{SRC}}", "{{DST}}")
"""

SRC="textures"
DST="src/main/resources/assets/steambly/textures"

def export(src, dst):
    print(src)
    print(">>> "+dst)
    subprocess.Popen([
        "gimp",
        "--no-interface",
        "--batch-interpreter=python-fu-eval",
        "-b",
        GIMPSCRIPT.replace("{{SRC}}", src).replace("{{DST}}", dst)])

def cp(src, dst):
    print(src)
    print(">>> "+dst)
    shutil.copy2(src, dst)

def exportdir(d):
    S = SRC+"/"+d
    D = DST+"/"+d

    for f in os.listdir(D):
        os.remove(D+"/"+f)

    for f in os.listdir(S):
        if f.endswith(".png.mcmeta"):
            cp(S+"/"+f, D+"/"+f);
        elif f.endswith(".xcf"):
            png = f.replace(".xcf", ".png")
            export(S+"/"+f, D+"/"+png)
        else:
            print("ERROR: Bad file name: "+f)

exportdir("blocks")
