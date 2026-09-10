import math

# deps[module] = list of modules it directly depends on (implementation(project(":x")))
deps = {
    "app": ["retrofit-base", "retrofit-deepl", "retrofit-openfoodfacts",
            "datasource-translation", "datasource-translation-network",
            "datasource-products", "datasource-products-network",
            "datasource-recipes", "datasource-recipes-network",
            "ui-common", "ui-home", "ui-scanner", "ui-saved"],
    "barcode-scanner-local": ["barcode-scanner"],
    "barcode-scanner": [],
    "datasource-products": ["datasource-common", "model-products"],
    "datasource-products-network": ["retrofit-openfoodfacts", "datasource-common",
                                     "datasource-products", "model-products"],
    "ui-scanner": ["barcode-scanner", "barcode-scanner-local", "domain-common",
                   "domain-products", "ui-common"],
    "domain-common": ["datasource-common", "datasource-translation",
                       "datasource-products", "model-deepl", "model-products"],
    "retrofit-recipes": ["retrofit-base", "model-recipes"],
    "ui-home": ["domain-common", "domain-recipes", "ui-common"],
    "ui-common": ["domain-common", "domain-recipes"],
    "datasource-recipes": ["datasource-common", "model-recipes"],
    "datasource-recipes-network": ["retrofit-recipes", "datasource-common",
                                    "datasource-recipes", "model-recipes"],
    "datasource-translation": ["datasource-common", "model-deepl"],
    "datasource-translation-network": ["retrofit-deepl", "datasource-common",
                                        "datasource-translation", "model-deepl"],
    "domain-recipes": ["datasource-common", "datasource-recipes", "model-recipes",
                        "domain-common", "database"],
    "retrofit-deepl": ["retrofit-base", "model-deepl"],
    "retrofit-openfoodfacts": ["retrofit-base", "model-products"],
    "ui-saved": ["domain-common", "domain-recipes", "ui-common"],
    "domain-products": ["datasource-common", "datasource-products", "domain-common",
                         "model-products", "database"],
    "datasource-common": [],
    "retrofit-base": [],
    "model-recipes": [],
    "model-products": [],
    "model-deepl": [],
    "database": [],
}

family = {
    "app": "app",
    "ui-home": "ui", "ui-scanner": "ui", "ui-saved": "ui", "ui-common": "ui",
    "domain-recipes": "domain", "domain-products": "domain", "domain-common": "domain",
    "datasource-recipes": "ds-c", "datasource-products": "ds-c",
    "datasource-translation": "ds-c", "datasource-common": "ds-c",
    "barcode-scanner": "barcode-c",
    "datasource-recipes-network": "ds-i", "datasource-products-network": "ds-i",
    "datasource-translation-network": "ds-i",
    "barcode-scanner-local": "barcode-i",
    "database": "db",
    "retrofit-recipes": "retrofit", "retrofit-deepl": "retrofit",
    "retrofit-openfoodfacts": "retrofit", "retrofit-base": "retrofit",
    "model-recipes": "model", "model-products": "model", "model-deepl": "model",
}

colors = {
    "app": "#12354F", "ui": "#4C7FA6", "domain": "#2E8B7F",
    "ds-c": "#5A8F3C", "barcode-c": "#8C8A1E",
    "ds-i": "#C06B12", "barcode-i": "#C99A1F",
    "db": "#B23A3A", "retrofit": "#7B4FA0", "model": "#5C666E",
}
text_on_fill = {  # true = white text (dark fill), false = dark text (light fill)
    "app": True, "ui": True, "domain": True, "ds-c": True, "barcode-c": True,
    "ds-i": True, "barcode-i": True, "db": True, "retrofit": True, "model": True,
}

violations = {
    ("domain-recipes", "database"), ("domain-products", "database"),
    ("domain-common", "datasource-products"),
    ("app", "datasource-recipes"), ("app", "datasource-recipes-network"),
    ("app", "datasource-products"), ("app", "datasource-products-network"),
    ("app", "datasource-translation"), ("app", "datasource-translation-network"),
    ("app", "retrofit-base"), ("app", "retrofit-deepl"), ("app", "retrofit-openfoodfacts"),
}

# --- rank = length of the longest outgoing path to a sink (leaf = rank 0) ----
_memo = {}


def rank(m):
    if m in _memo:
        return _memo[m]
    d = deps[m]
    r = 0 if not d else 1 + max(rank(x) for x in d)
    _memo[m] = r
    return r


for m in deps:
    rank(m)

max_rank = max(_memo.values())
rows = {}
for m, r in _memo.items():
    rows.setdefault(max_rank - r, []).append(m)  # row 0 = top (app), grows downward

print("rows:")
for row in sorted(rows):
    print(" ", row, sorted(rows[row]))

# --- barycenter sweeps to reduce edge crossings -------------------------
order = {row: sorted(names) for row, names in rows.items()}


def index_of(row):
    return {name: i for i, name in enumerate(order[row])}


preds = {m: [] for m in deps}
for m, ds in deps.items():
    for d in ds:
        preds[d].append(m)

for _ in range(8):
    # downward sweep: order row by barycenter of predecessors (row above)
    for row in range(1, max_rank + 1):
        above = index_of(row - 1)
        def key_down(m):
            ps = [p for p in preds[m] if p in above]
            return sum(above[p] for p in ps) / len(ps) if ps else index_of(row)[m]
        order[row] = sorted(order[row], key=key_down)
    # upward sweep: order row by barycenter of successors (row below)
    for row in range(max_rank - 1, -1, -1):
        below = index_of(row + 1)
        def key_up(m):
            ss = [s for s in deps[m] if s in below]
            return sum(below[s] for s in ss) / len(ss) if ss else index_of(row)[m]
        order[row] = sorted(order[row], key=key_up)

# --- coordinates ----------------------------------------------------------
SCALE = 2
FONT_SIZE = 9.6 * SCALE
CHAR_W = FONT_SIZE * 0.62
BOX_H = 40 * SCALE
BOX_PAD = 22 * SCALE
MIN_BOX_W = 100 * SCALE
COL_GAP = 40 * SCALE
ROW_GAP = 150 * SCALE
MARGIN_X, MARGIN_Y = 50 * SCALE, 45 * SCALE

box_w = {m: max(MIN_BOX_W, len(m) * CHAR_W + BOX_PAD) for m in deps}

max_row_w = max(sum(box_w[m] for m in names) + (len(names) - 1) * COL_GAP for names in order.values())
canvas_w = MARGIN_X * 2 + max_row_w
canvas_h = MARGIN_Y * 2 + (max_rank + 1) * ROW_GAP + 210 * SCALE

pos = {}
for row, names in order.items():
    row_w = sum(box_w[m] for m in names) + (len(names) - 1) * COL_GAP
    start_x = MARGIN_X + (max_row_w - row_w) / 2
    y = MARGIN_Y + row * ROW_GAP
    cursor = start_x
    for name in names:
        w = box_w[name]
        pos[name] = (cursor + w / 2, y)  # store CENTER-top point
        cursor += w + COL_GAP

# --- svg --------------------------------------------------------------
svg = []
svg.append(f'<svg xmlns="http://www.w3.org/2000/svg" width="{canvas_w:.0f}" height="{canvas_h:.0f}" '
            f'viewBox="0 0 {canvas_w:.0f} {canvas_h:.0f}" font-family="Consolas, \'DejaVu Sans Mono\', monospace">')
svg.append(f'<rect width="100%" height="100%" fill="#FFFFFF"/>')
svg.append('<defs>'
            '<marker id="ar" viewBox="0 0 10 10" refX="9.2" refY="5" markerWidth="6" markerHeight="6" '
            'orient="auto-start-reverse"><path d="M 0 1 L 10 5 L 0 9 z" fill="#7E8B95"/></marker>'
            '<marker id="arv" viewBox="0 0 10 10" refX="9.2" refY="5" markerWidth="6" markerHeight="6" '
            'orient="auto-start-reverse"><path d="M 0 1 L 10 5 L 0 9 z" fill="#C0392B"/></marker>'
            '</defs>')


def waypoints(src, dst):
    r1, r2 = _memo[src], _memo[dst]  # note: original rank (0=leaf); row = max_rank-rank
    row1, row2 = max_rank - r1, max_rank - r2
    sx, sy = pos[src]
    dx, dy = pos[dst]
    sy_bottom = sy + BOX_H
    dy_top = dy
    pts = [(sx, sy_bottom)]
    steps = row2 - row1
    for k in range(1, steps):
        t = k / steps
        wx = sx + (dx - sx) * t
        wy = sy_bottom + (dy_top - sy_bottom) * t
        pts.append((wx, wy))
    pts.append((dx, dy_top))
    return pts


def smooth_path(pts):
    # Catmull-Rom -> cubic Bezier: one continuous flowing curve through all
    # waypoints instead of a kinked chain of independently-mirrored segments.
    if len(pts) == 2:
        (x0, y0), (x1, y1) = pts
        my = (y0 + y1) / 2
        return f"M {x0:.1f} {y0:.1f} C {x0:.1f} {my:.1f} {x1:.1f} {my:.1f} {x1:.1f} {y1:.1f}"
    p = [pts[0]] + list(pts) + [pts[-1]]
    d = f"M {p[1][0]:.1f} {p[1][1]:.1f}"
    for i in range(1, len(p) - 2):
        p0, p1, p2, p3 = p[i - 1], p[i], p[i + 1], p[i + 2]
        c1x = p1[0] + (p2[0] - p0[0]) / 6
        c1y = p1[1] + (p2[1] - p0[1]) / 6
        c2x = p2[0] - (p3[0] - p1[0]) / 6
        c2y = p2[1] - (p3[1] - p1[1]) / 6
        d += f" C {c1x:.1f} {c1y:.1f} {c2x:.1f} {c2y:.1f} {p2[0]:.1f} {p2[1]:.1f}"
    return d


edge_list = [(m, d) for m in deps for d in deps[m]]
# normal edges first, violations drawn on top
normal = [e for e in edge_list if e not in violations]
viol = [e for e in edge_list if e in violations]

for src, dst in normal:
    d = smooth_path(waypoints(src, dst))
    svg.append(f'<path d="{d}" fill="none" stroke="#AAB4BC" stroke-width="{0.85*SCALE}" '
                f'stroke-opacity="0.48" marker-end="url(#ar)"/>')
for src, dst in viol:
    d = smooth_path(waypoints(src, dst))
    svg.append(f'<path d="{d}" fill="none" stroke="#C0392B" stroke-width="{1.6*SCALE}" '
                f'stroke-dasharray="{5*SCALE},{4*SCALE}" stroke-opacity="0.95" marker-end="url(#arv)"/>')

for m, (cx, y) in pos.items():
    fam = family[m]
    color = colors[fam]
    w = box_w[m]
    x = cx - w / 2
    svg.append(f'<rect x="{x:.1f}" y="{y:.1f}" width="{w:.1f}" height="{BOX_H}" rx="{6*SCALE}" ry="{6*SCALE}" '
                f'fill="{color}" stroke="{color}" stroke-width="{1.15*SCALE}"/>')
    svg.append(f'<text x="{cx:.1f}" y="{y + BOX_H/2 + 4.5*SCALE:.1f}" text-anchor="middle" '
                f'font-size="{FONT_SIZE}" fill="#FFFFFF">{m}</text>')

# legend
legend_y0 = canvas_h - 155 * SCALE
lx = MARGIN_X
svg.append(f'<text x="{lx}" y="{legend_y0:.1f}" font-family="Segoe UI, Arial, sans-serif" '
            f'font-size="{12.5*SCALE}" font-weight="600" fill="#3E4A54">Legenda:</text>')
fam_labels = [
    ("app", "app"), ("ui", "warstwa UI"), ("domain", "warstwa domeny"),
    ("ds-c", "datasource (kontrakt)"), ("barcode-c", "barcode (kontrakt)"),
    ("ds-i", "datasource (impl.)"), ("barcode-i", "barcode (impl.)"),
    ("db", "Room"), ("retrofit", "retrofit / sieć"), ("model", "modele (JVM)"),
]
col_w = max(230, canvas_w / 4 / SCALE) * SCALE
per_row = 4
for i, (fam, label) in enumerate(fam_labels):
    col = i % per_row
    rowi = i // per_row
    bx = lx + col * col_w
    by = legend_y0 + 10 * SCALE + rowi * 22 * SCALE
    svg.append(f'<rect x="{bx:.1f}" y="{by:.1f}" width="{22*SCALE}" height="{15*SCALE}" rx="{4*SCALE}" '
                f'fill="{colors[fam]}"/>')
    svg.append(f'<text x="{bx+29*SCALE:.1f}" y="{by+12*SCALE:.1f}" font-family="Segoe UI, Arial, sans-serif" '
                f'font-size="{12.5*SCALE}" fill="#3E4A54">{label}</text>')

viol_y = legend_y0 + 10 * SCALE + (math.ceil(len(fam_labels)/per_row)) * 22 * SCALE + 6 * SCALE
svg.append(f'<line x1="{lx}" y1="{viol_y:.1f}" x2="{lx+22*SCALE:.1f}" y2="{viol_y:.1f}" stroke="#C0392B" '
            f'stroke-width="{1.6*SCALE}" stroke-dasharray="{5*SCALE},{4*SCALE}"/>')
svg.append(f'<text x="{lx+29*SCALE:.1f}" y="{viol_y+4*SCALE:.1f}" font-family="Segoe UI, Arial, sans-serif" '
            f'font-size="{12.5*SCALE}" fill="#3E4A54">naruszenie warstwowej struktury (podrozdz. 1.4 / 3.1)</text>')

note_y = viol_y + 20 * SCALE
svg.append(f'<text x="{lx}" y="{note_y:.1f}" font-family="Segoe UI, Arial, sans-serif" font-size="{11*SCALE}" '
            f'fill="#6B757D">Strzałka prowadzi od modułu zależnego do modułu, od którego on zależy.</text>')
svg.append(f'<text x="{lx}" y="{note_y+16*SCALE:.1f}" font-family="Segoe UI, Arial, sans-serif" font-size="{11*SCALE}" '
            f'fill="#6B757D">Położenie w pionie wynika z długości najdłuższej ścieżki zależności wychodzącej z modułu — moduły w dolnym rzędzie nie zależą od żadnego innego modułu.</text>')

svg.append('</svg>')

out = "\n".join(svg)
with open("C:/Users/Sebastian/AndroidStudioProjects/recipe.me/audit/figures/module_graph_signal_style.svg",
          "w", encoding="utf-8") as f:
    f.write(out)

print("canvas", canvas_w, canvas_h, "rows", max_rank + 1, "edges", len(edge_list), "violations", len(viol))
