#!/usr/bin/env python3
"""
Generate 32x32 item textures for WeaponMod Minecraft Mod.
Replaces all 16x16 textures with detailed 32x32 pixel art.
"""

from PIL import Image, ImageDraw
import os

OUT = "/home/user/weaponmod/src/main/resources/assets/weaponmod/textures/item"

def new_img():
    return Image.new('RGBA', (32, 32), (0, 0, 0, 0))

def save(img, name):
    img.save(os.path.join(OUT, name))
    print(f"  {name}")

# ── Color palette ──────────────────────────────────────────────
BLK   = (12, 12, 12, 255)
DGRAY = (42, 42, 42, 255)
MGRAY = (72, 72, 72, 255)
LGRAY = (108, 108, 108, 255)
SLVR  = (168, 168, 168, 255)
HLIT  = (208, 208, 208, 255)
WITE  = (232, 232, 232, 255)

DWWD  = (52, 26, 8, 255)
MWWD  = (105, 55, 18, 255)
LWWD  = (148, 88, 36, 255)
KWWD  = (192, 142, 86, 255)

DMAG  = (26, 26, 26, 255)
MMAG  = (46, 46, 46, 255)
LMAG  = (66, 66, 66, 255)

DBRS  = (152, 108, 4, 255)
MBRS  = (192, 152, 16, 255)
LBRS  = (224, 184, 32, 255)

DGRN  = (28, 52, 10, 255)
MGRN  = (50, 86, 16, 255)
LGRN  = (84, 128, 30, 255)

DRED  = (112, 0, 0, 255)
MRED  = (172, 0, 0, 255)
LRED  = (212, 44, 44, 255)

ORNG  = (222, 88, 0, 255)
YLOW  = (212, 182, 0, 255)
TRCE  = (238, 52, 0, 255)

BLUE  = (46, 108, 152, 255)
LBLU  = (78, 152, 208, 255)
LAZR  = (0, 182, 242, 255)

DGRP  = (58, 44, 28, 255)
MGRP  = (86, 66, 46, 255)
LGRP  = (112, 88, 62, 255)

SMKG  = (118, 118, 118, 255)
SMKL  = (158, 158, 158, 255)

OLIV  = (78, 98, 36, 255)

TRNSP = (0, 0, 0, 0)


# ══════════════════════════════════════════════════════════════
# PISTOL
# ══════════════════════════════════════════════════════════════
def make_pistol():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Barrel extension
    d.rectangle([22, 11, 30, 14], fill=LGRAY, outline=DGRAY)
    # Slide
    d.rectangle([6, 8, 24, 15], fill=MGRAY, outline=DGRAY)
    # Slide highlight stripe
    d.line([7, 9, 23, 9], fill=SLVR)
    # Ejection port (cutout)
    d.rectangle([14, 9, 18, 14], fill=BLK)
    # Front sight
    d.rectangle([22, 6, 24, 9], fill=DGRAY)
    # Rear sight notch
    d.rectangle([6, 6, 8, 9], fill=DGRAY)
    d.rectangle([10, 6, 12, 9], fill=DGRAY)
    # Frame
    d.rectangle([8, 14, 20, 18], fill=LGRAY, outline=DGRAY)
    # Trigger guard outline
    d.rectangle([8, 18, 17, 23], fill=LGRAY, outline=DGRAY)
    d.rectangle([10, 19, 15, 22], fill=TRNSP)
    # Trigger
    d.line([12, 17, 11, 22], fill=SLVR)
    # Grip
    d.rectangle([14, 17, 21, 29], fill=MGRP, outline=DGRAY)
    # Grip texture
    for gy in range(19, 28, 3):
        d.line([15, gy, 20, gy], fill=DGRP)
    # Magazine base
    d.rectangle([14, 27, 21, 30], fill=DMAG, outline=DGRAY)

    return img


# ══════════════════════════════════════════════════════════════
# REVOLVER
# ══════════════════════════════════════════════════════════════
def make_revolver():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Barrel
    d.rectangle([18, 9, 30, 13], fill=MGRAY, outline=DGRAY)
    d.line([19, 10, 29, 10], fill=SLVR)

    # Top strap
    d.rectangle([8, 7, 22, 10], fill=MGRAY, outline=DGRAY)

    # Cylinder
    d.ellipse([11, 10, 22, 22], fill=LGRAY, outline=DGRAY)
    # Cylinder chambers (6 holes)
    chambers = [(13,12), (16,12), (19,12), (13,17), (16,17), (19,17)]
    for cx, cy in chambers:
        d.ellipse([cx, cy, cx+2, cy+2], fill=BLK)
    # Cylinder center pin
    d.ellipse([15, 14, 18, 17], fill=SLVR, outline=DGRAY)

    # Frame bottom
    d.rectangle([8, 21, 18, 24], fill=MGRAY, outline=DGRAY)

    # Trigger guard
    d.rectangle([8, 23, 16, 28], fill=LGRAY, outline=DGRAY)
    d.rectangle([10, 24, 14, 27], fill=TRNSP)

    # Hammer
    d.rectangle([6, 5, 10, 10], fill=DGRAY, outline=BLK)

    # Grip - wood
    d.rectangle([10, 23, 18, 31], fill=MWWD, outline=DGRAY)
    for gy in range(25, 30, 2):
        d.line([11, gy, 17, gy], fill=DWWD)

    return img


# ══════════════════════════════════════════════════════════════
# AK47
# ══════════════════════════════════════════════════════════════
def make_ak47():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Stock (wood, rear left)
    d.rectangle([1, 10, 10, 18], fill=MWWD, outline=DGRAY)
    d.rectangle([1, 12, 10, 16], fill=LWWD)
    d.line([2, 11, 9, 11], fill=KWWD)
    d.rectangle([1, 17, 10, 20], fill=MWWD, outline=DGRAY)  # lower stock rail

    # Receiver body
    d.rectangle([10, 9, 22, 17], fill=MGRAY, outline=DGRAY)
    d.line([11, 10, 21, 10], fill=SLVR)
    # Receiver top detail
    d.rectangle([14, 7, 20, 10], fill=DGRAY)  # dust cover area

    # Pistol grip (polymer)
    d.polygon([(14, 17), (14, 27), (20, 29), (21, 17)], fill=DGRP, outline=DGRAY)
    for gy in range(19, 27, 3):
        d.line([15, gy, 20, gy+1], fill=MGRAY)

    # Barrel + gas tube (extending right)
    d.rectangle([22, 11, 30, 14], fill=LGRAY, outline=DGRAY)  # barrel
    d.rectangle([22, 9, 28, 12], fill=MGRAY, outline=DGRAY)   # gas tube

    # Front sight block
    d.rectangle([28, 8, 31, 14], fill=DGRAY)

    # Handguard (wood)
    d.rectangle([16, 14, 24, 18], fill=MWWD, outline=DGRAY)
    d.line([17, 15, 23, 15], fill=LWWD)

    # Magazine (curved, below receiver)
    # Curve: wider at top, slightly curved
    d.polygon(
        [(14, 17), (20, 17), (21, 26), (19, 28), (15, 28), (13, 26)],
        fill=DMAG, outline=DGRAY
    )
    d.line([15, 18, 19, 18], fill=LMAG)
    d.line([16, 27, 18, 27], fill=MMAG)

    # Selector switch
    d.line([10, 13, 10, 16], fill=BLK)
    d.ellipse([9, 12, 11, 14], fill=LGRAY)

    return img


# ══════════════════════════════════════════════════════════════
# MP5
# ══════════════════════════════════════════════════════════════
def make_mp5():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Folding stock (retracted, back end)
    d.rectangle([1, 11, 8, 16], fill=DGRAY, outline=BLK)
    d.line([2, 12, 7, 12], fill=LGRAY)
    d.line([2, 15, 7, 15], fill=LGRAY)

    # Upper receiver
    d.rectangle([7, 8, 26, 14], fill=MGRAY, outline=DGRAY)
    d.line([8, 9, 25, 9], fill=SLVR)

    # Charging handle slot
    d.rectangle([20, 9, 22, 13], fill=BLK)

    # Barrel + cocking tube
    d.rectangle([26, 10, 31, 13], fill=LGRAY, outline=DGRAY)

    # Lower receiver
    d.rectangle([8, 14, 22, 18], fill=LGRAY, outline=DGRAY)

    # Pistol grip (polymer)
    d.polygon([(13, 18), (13, 28), (19, 30), (20, 18)], fill=DGRP, outline=DGRAY)
    for gy in range(20, 28, 3):
        d.line([14, gy, 19, gy], fill=MGRAY)

    # Trigger guard
    d.rectangle([9, 18, 16, 22], fill=LGRAY, outline=DGRAY)
    d.rectangle([11, 19, 14, 21], fill=TRNSP)

    # Magazine (straight, below lower receiver)
    d.rectangle([14, 18, 20, 30], fill=DMAG, outline=DGRAY)
    d.line([15, 19, 19, 19], fill=LMAG)
    d.line([15, 29, 19, 29], fill=MMAG)

    # Front grip / handguard
    d.rectangle([18, 14, 26, 18], fill=MGRAY, outline=DGRAY)
    d.line([19, 15, 25, 15], fill=SLVR)

    # Sights
    d.rectangle([9, 6, 12, 9], fill=DGRAY)   # rear
    d.rectangle([24, 6, 26, 9], fill=DGRAY)  # front

    return img


# ══════════════════════════════════════════════════════════════
# SHOTGUN
# ══════════════════════════════════════════════════════════════
def make_shotgun():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Stock (wood, rear left)
    d.rectangle([1, 10, 10, 20], fill=MWWD, outline=DGRAY)
    d.rectangle([2, 12, 9, 18], fill=LWWD)
    d.line([3, 11, 8, 11], fill=KWWD)

    # Receiver
    d.rectangle([9, 9, 20, 17], fill=MGRAY, outline=DGRAY)
    d.line([10, 10, 19, 10], fill=SLVR)

    # Action / ejection port
    d.rectangle([12, 10, 16, 16], fill=BLK)

    # Barrel (long, one tube)
    d.rectangle([19, 10, 31, 13], fill=LGRAY, outline=DGRAY)
    d.line([20, 11, 30, 11], fill=HLIT)

    # Pump handguard (wood, slides)
    d.rectangle([19, 14, 28, 18], fill=MWWD, outline=DGRAY)
    d.line([20, 15, 27, 15], fill=LWWD)

    # Trigger guard
    d.rectangle([10, 17, 17, 22], fill=LGRAY, outline=DGRAY)
    d.rectangle([12, 18, 15, 21], fill=TRNSP)

    # Pistol grip / trigger area
    d.rectangle([12, 17, 18, 27], fill=DGRP, outline=DGRAY)
    for gy in range(19, 26, 3):
        d.line([13, gy, 17, gy], fill=MGRP)

    # Bead sight (front)
    d.ellipse([29, 9, 31, 11], fill=HLIT)

    return img


# ══════════════════════════════════════════════════════════════
# SNIPER
# ══════════════════════════════════════════════════════════════
def make_sniper():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Stock (rear, wood)
    d.rectangle([1, 11, 8, 22], fill=MWWD, outline=DGRAY)
    d.rectangle([2, 13, 7, 20], fill=LWWD)
    d.line([3, 12, 6, 12], fill=KWWD)

    # Cheekpiece
    d.rectangle([3, 9, 9, 13], fill=MWWD, outline=DGRAY)

    # Receiver / action
    d.rectangle([8, 9, 22, 17], fill=MGRAY, outline=DGRAY)
    d.line([9, 10, 21, 10], fill=SLVR)

    # Scope rails / action top
    d.rectangle([10, 7, 20, 10], fill=DGRAY, outline=BLK)

    # Scope
    d.rectangle([10, 4, 20, 8], fill=DGRAY, outline=BLK)
    d.ellipse([18, 3, 22, 9], fill=MGRAY, outline=DGRAY)  # objective lens
    d.ellipse([8, 3, 12, 9], fill=MGRAY, outline=DGRAY)   # ocular lens
    d.ellipse([9, 4, 11, 8], fill=BLUE, outline=DGRAY)    # glass
    d.ellipse([19, 4, 21, 8], fill=BLUE, outline=DGRAY)   # glass

    # Barrel (very long)
    d.rectangle([21, 11, 31, 14], fill=LGRAY, outline=DGRAY)
    d.line([22, 12, 30, 12], fill=HLIT)

    # Muzzle brake
    d.rectangle([30, 10, 31, 15], fill=DGRAY)

    # Trigger guard
    d.rectangle([9, 17, 16, 22], fill=LGRAY, outline=DGRAY)
    d.rectangle([11, 18, 14, 21], fill=TRNSP)

    # Pistol grip
    d.polygon([(12, 17), (12, 28), (18, 30), (19, 17)], fill=DGRP, outline=DGRAY)
    for gy in range(19, 27, 3):
        d.line([13, gy, 18, gy], fill=MGRP)

    # Bipod (folded up on barrel)
    d.line([23, 14, 22, 18], fill=DGRAY)
    d.line([26, 14, 27, 18], fill=DGRAY)

    # Magazine
    d.rectangle([14, 17, 20, 27], fill=DMAG, outline=DGRAY)
    d.line([15, 18, 19, 18], fill=LMAG)

    return img


# ══════════════════════════════════════════════════════════════
# BASEBALL BAT
# ══════════════════════════════════════════════════════════════
def make_baseball_bat():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Draw diagonally from bottom-left to top-right
    # Handle (narrow, bottom-left)
    d.line([4, 28, 8, 24], fill=MWWD, width=3)
    d.line([4, 28, 8, 24], fill=LWWD, width=1)

    # Grip wrap
    for i in range(3):
        x0 = 4 + i*1
        y0 = 28 - i*1
        d.line([x0, y0, x0+3, y0-3], fill=DGRP)

    # Shaft (medium width)
    d.polygon(
        [(5, 30), (9, 26), (14, 21), (17, 24), (11, 30)],
        fill=MWWD, outline=DWWD
    )
    d.line([7, 27, 15, 19], fill=LWWD)

    # Barrel (wide hitting end, top-right)
    d.ellipse([13, 6, 29, 22], fill=MWWD, outline=DWWD)
    # Highlight on barrel
    d.ellipse([15, 8, 25, 16], fill=LWWD)
    d.ellipse([17, 10, 22, 14], fill=KWWD)
    # Wood grain lines on barrel
    d.line([16, 9, 24, 17], fill=DWWD)
    d.line([14, 11, 20, 19], fill=DWWD)
    d.line([18, 8, 27, 17], fill=DWWD)

    # Brand mark on barrel
    d.ellipse([18, 11, 24, 17], fill=MWWD, outline=DWWD)

    # Knob at handle end
    d.ellipse([2, 27, 8, 31], fill=MWWD, outline=DWWD)

    return img


# ══════════════════════════════════════════════════════════════
# COMBAT KNIFE
# ══════════════════════════════════════════════════════════════
def make_combat_knife():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Handle (bottom-left, diagonal)
    d.polygon(
        [(3, 28), (3, 24), (9, 18), (11, 20)],
        fill=DGRP, outline=DGRAY
    )
    # Handle texture
    for i in range(3):
        x = 3 + i*2
        y = 27 - i*2
        d.line([x, y, x+2, y-2], fill=MGRP)

    # Guard (crossguard)
    d.polygon([(8, 21), (6, 18), (13, 11), (15, 14)], fill=LGRAY, outline=DGRAY)
    d.polygon([(10, 19), (12, 22), (15, 19), (13, 16)], fill=LGRAY, outline=DGRAY)

    # Blade (long, tapering to a point top-right)
    d.polygon(
        [(11, 20), (13, 18), (29, 4), (27, 4)],
        fill=SLVR, outline=LGRAY
    )
    # Edge highlight
    d.line([11, 20, 29, 4], fill=HLIT)
    # Spine / back edge
    d.polygon(
        [(10, 21), (12, 19), (28, 5), (30, 3), (29, 4), (27, 4)],
        fill=LGRAY, outline=DGRAY
    )
    # Blade fuller (blood groove)
    d.line([12, 19, 27, 5], fill=SLVR)

    return img


# ══════════════════════════════════════════════════════════════
# MACHETE
# ══════════════════════════════════════════════════════════════
def make_machete():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Handle (bottom-left)
    d.polygon(
        [(2, 30), (2, 24), (8, 18), (11, 21)],
        fill=MWWD, outline=DWWD
    )
    for i in range(3):
        d.line([2+i, 29-i*2, 5+i, 26-i*2], fill=LWWD)

    # Guard
    d.polygon([(7, 22), (5, 19), (11, 13), (13, 16)], fill=LGRAY, outline=DGRAY)

    # Wide blade (machete is much wider than knife)
    d.polygon(
        [(10, 21), (12, 19),
         (30, 5), (28, 3),     # tip
         (30, 8),              # back of tip
         (25, 14), (14, 24)],  # curved back edge
        fill=SLVR, outline=DGRAY
    )
    # Blade highlight (edge line)
    d.line([10, 21, 29, 4], fill=HLIT)
    # Subtle fuller
    d.line([13, 19, 27, 6], fill=LGRAY)

    return img


# ══════════════════════════════════════════════════════════════
# FRAG GRENADE
# ══════════════════════════════════════════════════════════════
def make_frag_grenade():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Body (pineapple grenade shape)
    d.ellipse([6, 10, 26, 26], fill=MGRN, outline=DGRN)

    # Segmented pattern
    for gy in [13, 16, 19, 22]:
        d.line([7, gy, 25, gy], fill=DGRN)
    for gx in [10, 14, 18, 22]:
        d.line([gx, 11, gx, 25], fill=DGRN)

    # Safety lever
    d.rectangle([9, 8, 15, 11], fill=LGRAY, outline=DGRAY)
    d.line([10, 9, 14, 9], fill=SLVR)

    # Fuse assembly (top)
    d.rectangle([14, 4, 18, 11], fill=DGRAY, outline=BLK)
    d.rectangle([15, 2, 17, 5], fill=MGRAY)

    # Safety pin ring
    d.ellipse([16, 1, 22, 7], outline=LGRAY, width=2)
    d.line([16, 4, 19, 4], fill=LGRAY)

    # Body highlight
    d.ellipse([9, 12, 16, 19], fill=LGRN, outline=TRNSP)

    return img


# ══════════════════════════════════════════════════════════════
# FLASH GRENADE
# ══════════════════════════════════════════════════════════════
def make_flash_grenade():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Cylindrical body
    d.ellipse([7, 22, 24, 28], fill=DGRAY, outline=BLK)  # bottom cap
    d.rectangle([7, 8, 24, 25], fill=LGRAY, outline=DGRAY)
    d.ellipse([7, 5, 24, 11], fill=SLVR, outline=DGRAY)  # top cap

    # Yellow warning stripe
    d.rectangle([7, 17, 24, 21], fill=YLOW, outline=DGRAY)

    # Body highlight
    d.line([9, 9, 9, 24], fill=HLIT)

    # Safety lever
    d.rectangle([10, 5, 15, 8], fill=LGRAY, outline=DGRAY)
    d.line([11, 6, 14, 6], fill=SLVR)

    # Fuse / detonator top
    d.rectangle([14, 2, 17, 6], fill=MGRAY, outline=DGRAY)

    # Safety pin ring
    d.ellipse([16, 1, 22, 7], outline=LGRAY, width=2)
    d.line([16, 4, 19, 4], fill=LGRAY)

    # Flash holes at top
    for x in [10, 14, 18, 22]:
        d.ellipse([x-1, 5, x+1, 7], fill=BLK)

    return img


# ══════════════════════════════════════════════════════════════
# SMOKE GRENADE
# ══════════════════════════════════════════════════════════════
def make_smoke_grenade():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Cylindrical body
    d.ellipse([7, 22, 24, 28], fill=DGRAY, outline=BLK)
    d.rectangle([7, 8, 24, 25], fill=SMKG, outline=DGRAY)
    d.ellipse([7, 5, 24, 11], fill=SMKL, outline=DGRAY)

    # Color band (ID stripe) – orange
    d.rectangle([7, 16, 24, 20], fill=ORNG, outline=DGRAY)

    # Body highlight
    d.line([9, 9, 9, 24], fill=SMKL)

    # Pull-ring hole
    d.rectangle([9, 9, 14, 12], fill=LGRAY, outline=DGRAY)
    d.line([10, 10, 13, 10], fill=SLVR)

    # Fuse top
    d.rectangle([13, 3, 17, 7], fill=MGRAY, outline=DGRAY)

    # Smoke vent holes at top
    for x in [9, 13, 17, 21]:
        d.ellipse([x-1, 5, x+1, 7], fill=BLK)

    # Safety pin
    d.ellipse([16, 2, 22, 8], outline=LGRAY, width=2)
    d.line([16, 5, 19, 5], fill=LGRAY)

    # Smoke puff hint at top
    d.ellipse([13, 0, 18, 5], fill=(210, 210, 210, 128))

    return img


# ══════════════════════════════════════════════════════════════
# AK47 MAGAZINE
# ══════════════════════════════════════════════════════════════
def make_ak47_magazine():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Curved "banana" magazine
    # Top (attachment area)
    d.rectangle([10, 2, 22, 8], fill=MMAG, outline=DMAG)
    d.line([11, 3, 21, 3], fill=LMAG)

    # Upper body (straight portion)
    d.rectangle([10, 7, 22, 16], fill=MMAG, outline=DMAG)

    # Curved lower body (curving left as it goes down)
    d.polygon(
        [(10, 15), (22, 15),
         (20, 24), (18, 28), (14, 30), (10, 30),
         (8, 28), (8, 20)],
        fill=MMAG, outline=DMAG
    )

    # Highlight stripe on front face
    d.line([11, 3, 10, 29], fill=LMAG)

    # Follower spring hint
    d.line([12, 26, 20, 26], fill=LGRAY)

    # Ribs on body
    for ry in [9, 12, 15, 18, 21]:
        d.line([11, ry, 21, ry], fill=DMAG)

    return img


# ══════════════════════════════════════════════════════════════
# MP5 MAGAZINE
# ══════════════════════════════════════════════════════════════
def make_mp5_magazine():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Straight double-stack magazine
    d.rectangle([10, 2, 22, 30], fill=MMAG, outline=DMAG)

    # Feed lips at top
    d.rectangle([9, 2, 23, 5], fill=DGRAY, outline=BLK)
    d.line([15, 2, 17, 5], fill=TRNSP)  # gap between lips

    # Rib lines
    for ry in [6, 10, 14, 18, 22, 26]:
        d.line([11, ry, 21, ry], fill=DMAG)

    # Front/back highlight
    d.line([11, 3, 11, 29], fill=LMAG)

    # Round counter holes (side windows)
    for ry in [8, 14, 20, 26]:
        d.ellipse([18, ry-1, 21, ry+1], fill=BLK)

    # Base plate
    d.rectangle([9, 29, 23, 31], fill=LGRAY, outline=DGRAY)

    return img


# ══════════════════════════════════════════════════════════════
# PISTOL MAGAZINE
# ══════════════════════════════════════════════════════════════
def make_pistol_magazine():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Slightly tapered magazine (wider at top)
    d.polygon(
        [(9, 2), (23, 2), (22, 30), (10, 30)],
        fill=MMAG, outline=DMAG
    )

    # Feed lips
    d.rectangle([8, 2, 24, 5], fill=DGRAY, outline=BLK)

    # Highlight
    d.line([10, 3, 10, 29], fill=LMAG)

    # Ribs
    for ry in [7, 12, 17, 22, 27]:
        d.line([11, ry, 21, ry], fill=DMAG)

    # Witness holes
    for ry in [9, 15, 21]:
        d.ellipse([19, ry-1, 22, ry+1], fill=BLK)

    # Base plate
    d.rectangle([8, 29, 24, 31], fill=LGRAY, outline=DGRAY)

    return img


# ══════════════════════════════════════════════════════════════
# SNIPER MAGAZINE
# ══════════════════════════════════════════════════════════════
def make_sniper_magazine():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Wide box magazine (sniper uses larger rounds)
    d.rectangle([7, 2, 25, 30], fill=MMAG, outline=DMAG)

    # Feed lips
    d.rectangle([6, 2, 26, 6], fill=DGRAY, outline=BLK)
    d.line([15, 2, 17, 6], fill=TRNSP)

    # Center spine
    d.line([16, 6, 16, 30], fill=DMAG)

    # Highlight
    d.line([8, 3, 8, 29], fill=LMAG)

    # Ribs
    for ry in [8, 13, 18, 23, 28]:
        d.line([8, ry, 24, ry], fill=DMAG)

    # Witness holes
    for ry in [10, 17, 24]:
        d.ellipse([20, ry-1, 24, ry+1], fill=BLK)

    # Base
    d.rectangle([6, 29, 26, 31], fill=LGRAY, outline=DGRAY)

    return img


# ══════════════════════════════════════════════════════════════
# AMMO - STANDARD (7.62×39 / generic rifle round)
# ══════════════════════════════════════════════════════════════
def make_ammo_standard():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Multiple stacked cartridges for "stack" visual
    offsets = [(3, 0), (0, 3), (6, 3)]
    for ox, oy in offsets:
        # Bullet tip (pointed)
        d.polygon(
            [(12+ox, 5+oy), (13+ox, 5+oy), (16+ox, 10+oy), (12+ox, 10+oy)],
            fill=MBRS, outline=DBRS
        )
        # Casing (brass)
        d.rectangle([11+ox, 10+oy, 17+ox, 24+oy], fill=MBRS, outline=DBRS)
        # Casing mouth crimp
        d.line([11+ox, 11+oy, 17+ox, 11+oy], fill=LBRS)
        # Extractor groove
        d.rectangle([11+ox, 22+oy, 17+ox, 23+oy], fill=DBRS)
        # Case head
        d.rectangle([10+ox, 23+oy, 18+ox, 26+oy], fill=MBRS, outline=DBRS)
        # Primer
        d.ellipse([12+ox, 24+oy, 16+ox, 26+oy], fill=SLVR)

    return img


# ══════════════════════════════════════════════════════════════
# AMMO - ARMOR PIERCING
# ══════════════════════════════════════════════════════════════
def make_ammo_ap():
    img = new_img()
    d = ImageDraw.Draw(img)

    offsets = [(3, 0), (0, 3), (6, 3)]
    for ox, oy in offsets:
        # Steel penetrator tip (dark gray)
        d.polygon(
            [(12+ox, 4+oy), (14+ox, 4+oy), (17+ox, 10+oy), (12+ox, 10+oy)],
            fill=DGRAY, outline=BLK
        )
        # Jacket
        d.rectangle([11+ox, 10+oy, 17+ox, 14+oy], fill=LGRAY, outline=DGRAY)
        # Brass casing
        d.rectangle([11+ox, 14+oy, 17+ox, 24+oy], fill=MBRS, outline=DBRS)
        # Crimp
        d.line([11+ox, 15+oy, 17+ox, 15+oy], fill=LBRS)
        # Extractor groove
        d.rectangle([11+ox, 22+oy, 17+ox, 23+oy], fill=DBRS)
        # Case head
        d.rectangle([10+ox, 23+oy, 18+ox, 26+oy], fill=MBRS, outline=DBRS)
        # Primer
        d.ellipse([12+ox, 24+oy, 16+ox, 26+oy], fill=SLVR)

    return img


# ══════════════════════════════════════════════════════════════
# AMMO - RUBBER
# ══════════════════════════════════════════════════════════════
def make_ammo_rubber():
    img = new_img()
    d = ImageDraw.Draw(img)

    offsets = [(3, 0), (0, 3), (6, 3)]
    for ox, oy in offsets:
        # Rubber tip (round/flat, dark)
        d.ellipse([11+ox, 5+oy, 17+ox, 12+oy], fill=DGRAY, outline=BLK)
        # Casing (lighter, non-lethal look)
        d.rectangle([11+ox, 11+oy, 17+ox, 24+oy], fill=SLVR, outline=LGRAY)
        # Crimp
        d.line([11+ox, 12+oy, 17+ox, 12+oy], fill=HLIT)
        # Extractor groove
        d.rectangle([11+ox, 22+oy, 17+ox, 23+oy], fill=LGRAY)
        # Case head
        d.rectangle([10+ox, 23+oy, 18+ox, 26+oy], fill=SLVR, outline=LGRAY)
        # Primer
        d.ellipse([12+ox, 24+oy, 16+ox, 26+oy], fill=HLIT)

    return img


# ══════════════════════════════════════════════════════════════
# AMMO - TRACER
# ══════════════════════════════════════════════════════════════
def make_ammo_tracer():
    img = new_img()
    d = ImageDraw.Draw(img)

    offsets = [(3, 0), (0, 3), (6, 3)]
    for ox, oy in offsets:
        # Red/orange tracer tip
        d.polygon(
            [(12+ox, 4+oy), (14+ox, 4+oy), (17+ox, 10+oy), (12+ox, 10+oy)],
            fill=TRCE, outline=DRED
        )
        # Jacket
        d.rectangle([11+ox, 10+oy, 17+ox, 14+oy], fill=LGRAY, outline=DGRAY)
        # Casing
        d.rectangle([11+ox, 14+oy, 17+ox, 24+oy], fill=MBRS, outline=DBRS)
        # Tracer compound visible at base of bullet
        d.line([11+ox, 13+oy, 17+ox, 13+oy], fill=TRCE)
        # Crimp
        d.line([11+ox, 15+oy, 17+ox, 15+oy], fill=LBRS)
        # Extractor groove
        d.rectangle([11+ox, 22+oy, 17+ox, 23+oy], fill=DBRS)
        # Case head (orange/red tinted for tracer)
        d.rectangle([10+ox, 23+oy, 18+ox, 26+oy], fill=ORNG, outline=DBRS)
        # Primer
        d.ellipse([12+ox, 24+oy, 16+ox, 26+oy], fill=SLVR)

    return img


# ══════════════════════════════════════════════════════════════
# PISTOL AMMO (9mm)
# ══════════════════════════════════════════════════════════════
def make_pistol_ammo():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Show 3 rounds
    offsets = [(2, 1), (6, 1), (10, 1)]
    for ox, oy in offsets:
        # Hollow point tip (round top)
        d.ellipse([11+ox, 7+oy, 16+ox, 13+oy], fill=LGRAY, outline=DGRAY)
        d.ellipse([12+ox, 8+oy, 15+ox, 12+oy], fill=BLK)  # hollow point cavity
        # Short brass case
        d.rectangle([11+ox, 12+oy, 16+ox, 22+oy], fill=MBRS, outline=DBRS)
        d.line([11+ox, 13+oy, 16+ox, 13+oy], fill=LBRS)
        # Extractor groove
        d.rectangle([11+ox, 20+oy, 16+ox, 21+oy], fill=DBRS)
        # Case head
        d.rectangle([10+ox, 21+oy, 17+ox, 24+oy], fill=MBRS, outline=DBRS)
        # Primer
        d.ellipse([12+ox, 22+oy, 15+ox, 24+oy], fill=SLVR)

    return img


# ══════════════════════════════════════════════════════════════
# RIFLE AMMO (.308 / 7.62×51)
# ══════════════════════════════════════════════════════════════
def make_rifle_ammo():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Single large cartridge (bolt-action / sniper round)
    # Bullet (pointed, boat-tail style)
    d.polygon(
        [(13, 2), (19, 2), (22, 8), (21, 12), (11, 12), (10, 8)],
        fill=LGRAY, outline=DGRAY
    )
    d.polygon(
        [(15, 2), (17, 2), (20, 7), (16, 8), (12, 7)],
        fill=SLVR
    )
    # Neck (case neck, smaller diameter)
    d.rectangle([13, 12, 19, 15], fill=MBRS, outline=DBRS)
    # Shoulder (taper)
    d.polygon([(12, 15), (20, 15), (21, 18), (11, 18)], fill=MBRS, outline=DBRS)
    # Case body
    d.rectangle([11, 18, 21, 27], fill=MBRS, outline=DBRS)
    d.line([12, 19, 20, 19], fill=LBRS)
    # Extractor groove
    d.rectangle([11, 25, 21, 26], fill=DBRS)
    # Case head (rimless, same diameter)
    d.rectangle([11, 26, 21, 29], fill=MBRS, outline=DBRS)
    # Primer
    d.ellipse([13, 27, 19, 29], fill=SLVR, outline=LGRAY)

    return img


# ══════════════════════════════════════════════════════════════
# SHOTGUN SHELLS
# ══════════════════════════════════════════════════════════════
def make_shotgun_shells():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Two shells side by side
    for ox in [3, 16]:
        # Hull (red plastic)
        d.rectangle([ox, 5, ox+11, 26], fill=MRED, outline=DRED)
        # Brass head
        d.rectangle([ox, 22, ox+11, 30], fill=MBRS, outline=DBRS)
        # Crimp (star crimp at top)
        d.polygon(
            [(ox+5, 2), (ox+7, 4), (ox+11, 4), (ox+9, 5),
             (ox+11, 7), (ox+8, 6), (ox+7, 8), (ox+5, 6),
             (ox+3, 8), (ox+2, 6), (ox+0, 7), (ox+2, 5),
             (ox+0, 4), (ox+3, 4)],
            fill=DRED, outline=DRED
        )
        # Hull shine
        d.line([ox+2, 6, ox+2, 21], fill=LRED)
        # Extractor groove
        d.rectangle([ox, 28, ox+11, 29], fill=DBRS)
        # Primer
        d.ellipse([ox+3, 27, ox+8, 30], fill=SLVR)
        # Shot size label
        d.rectangle([ox+2, 14, ox+9, 17], fill=DRED)
        d.line([ox+3, 15, ox+8, 15], fill=LRED)

    return img


# ══════════════════════════════════════════════════════════════
# SCOPE
# ══════════════════════════════════════════════════════════════
def make_scope():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Main tube
    d.rectangle([3, 13, 29, 19], fill=MGRAY, outline=DGRAY)
    d.line([4, 14, 28, 14], fill=SLVR)

    # Objective bell (large, right side)
    d.ellipse([22, 9, 31, 23], fill=LGRAY, outline=DGRAY)
    d.ellipse([24, 11, 30, 21], fill=MGRAY, outline=DGRAY)
    d.ellipse([25, 12, 29, 20], fill=BLUE, outline=DGRAY)    # objective glass
    d.ellipse([25, 13, 28, 18], fill=LBLU)                    # lens reflection

    # Ocular bell (smaller, left side)
    d.ellipse([1, 11, 9, 21], fill=LGRAY, outline=DGRAY)
    d.ellipse([2, 12, 8, 20], fill=MGRAY, outline=DGRAY)
    d.ellipse([2, 13, 7, 19], fill=BLUE, outline=DGRAY)
    d.ellipse([3, 14, 6, 18], fill=LBLU)

    # Elevation/windage turrets (top center)
    d.rectangle([13, 9, 19, 14], fill=LGRAY, outline=DGRAY)
    d.line([14, 10, 18, 10], fill=SLVR)
    d.line([14, 12, 18, 12], fill=DGRAY)

    # Parallax knob (right side of tube)
    d.rectangle([20, 13, 24, 19], fill=LGRAY, outline=DGRAY)
    d.line([21, 14, 23, 14], fill=SLVR)

    # Rings/mounts
    d.rectangle([7, 10, 12, 22], fill=MGRAY, outline=DGRAY)
    d.rectangle([18, 10, 23, 22], fill=MGRAY, outline=DGRAY)
    for rx in [7, 18]:
        d.line([rx, 13, rx+5, 13], fill=SLVR)
        d.line([rx, 19, rx+5, 19], fill=DGRAY)

    return img


# ══════════════════════════════════════════════════════════════
# SILENCER / SUPPRESSOR
# ══════════════════════════════════════════════════════════════
def make_silencer():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Main cylinder (long)
    d.rectangle([2, 12, 30, 20], fill=MGRAY, outline=DGRAY)

    # End caps
    d.ellipse([1, 11, 7, 21], fill=LGRAY, outline=DGRAY)
    d.ellipse([26, 11, 31, 21], fill=LGRAY, outline=DGRAY)

    # Muzzle opening
    d.ellipse([27, 13, 31, 19], fill=BLK)

    # Thread attachment end
    d.rectangle([1, 13, 5, 19], fill=DGRAY, outline=BLK)
    for tx in [2, 3, 4]:
        d.line([tx, 13, tx, 19], fill=LGRAY)

    # Baffle indicators (ridges on tube)
    for bx in [8, 13, 18, 23]:
        d.line([bx, 12, bx, 20], fill=DGRAY)

    # Tube highlight
    d.line([3, 13, 29, 13], fill=SLVR)
    d.line([3, 19, 29, 19], fill=DGRAY)

    return img


# ══════════════════════════════════════════════════════════════
# LASER SIGHT
# ══════════════════════════════════════════════════════════════
def make_laser():
    img = new_img()
    d = ImageDraw.Draw(img)

    # Main body (rectangular device)
    d.rectangle([4, 11, 22, 21], fill=DGRAY, outline=BLK)
    # Top face
    d.rectangle([4, 8, 22, 12], fill=MGRAY, outline=DGRAY)
    d.line([5, 9, 21, 9], fill=SLVR)

    # Laser emitter aperture (front, right)
    d.rectangle([22, 13, 28, 19], fill=BLK, outline=DGRAY)
    d.ellipse([23, 14, 27, 18], fill=DRED, outline=MRED)
    d.ellipse([24, 15, 26, 17], fill=LRED)

    # Laser beam
    d.line([28, 16, 31, 16], fill=MRED)
    d.line([29, 15, 31, 15], fill=LRED)
    d.line([29, 17, 31, 17], fill=LRED)

    # Activation button (top)
    d.ellipse([9, 7, 14, 11], fill=MRED, outline=DRED)
    d.ellipse([10, 8, 13, 10], fill=LRED)

    # Rail attachment (bottom)
    d.rectangle([5, 21, 21, 25], fill=LGRAY, outline=DGRAY)
    # Rail notches
    for rx in [7, 10, 13, 16, 19]:
        d.line([rx, 21, rx, 25], fill=DGRAY)

    # Cable/wire detail on side
    d.line([6, 12, 6, 20], fill=LGRAY)

    # Brand dot
    d.rectangle([16, 13, 20, 17], fill=BLK)
    d.line([17, 14, 19, 14], fill=MGRAY)
    d.line([17, 16, 19, 16], fill=MGRAY)

    return img


# ══════════════════════════════════════════════════════════════
# MAIN – generate all textures
# ══════════════════════════════════════════════════════════════
if __name__ == "__main__":
    os.makedirs(OUT, exist_ok=True)
    print("Generating 32x32 textures...")

    save(make_pistol(),           "pistol.png")
    save(make_revolver(),         "revolver.png")
    save(make_ak47(),             "ak47.png")
    save(make_mp5(),              "mp5.png")
    save(make_shotgun(),          "shotgun.png")
    save(make_sniper(),           "sniper.png")
    save(make_baseball_bat(),     "baseball_bat.png")
    save(make_combat_knife(),     "combat_knife.png")
    save(make_machete(),          "machete.png")
    save(make_frag_grenade(),     "frag_grenade.png")
    save(make_flash_grenade(),    "flash_grenade.png")
    save(make_smoke_grenade(),    "smoke_grenade.png")
    save(make_ak47_magazine(),    "ak47_magazine.png")
    save(make_mp5_magazine(),     "mp5_magazine.png")
    save(make_pistol_magazine(),  "pistol_magazine.png")
    save(make_sniper_magazine(),  "sniper_magazine.png")
    save(make_ammo_standard(),    "ammo_standard.png")
    save(make_ammo_ap(),          "ammo_ap.png")
    save(make_ammo_rubber(),      "ammo_rubber.png")
    save(make_ammo_tracer(),      "ammo_tracer.png")
    save(make_pistol_ammo(),      "pistol_ammo.png")
    save(make_rifle_ammo(),       "rifle_ammo.png")
    save(make_shotgun_shells(),   "shotgun_shells.png")
    save(make_scope(),            "scope.png")
    save(make_silencer(),         "silencer.png")
    save(make_laser(),            "laser.png")

    print(f"\nAll 26 textures saved to:\n  {OUT}")
